package com.example.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.BooksCountsDTO;
import com.example.DTO.OrderDTO;
import com.example.DTO.OrderResponseDTO;
import com.example.DTO.OrderUpdateDTO;
import com.example.DTO.ToOrderDTO;
import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;
import com.example.domain.Member;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.security.SecurityUtil;
import com.example.service.BookService;
import com.example.service.CartItemService;
import com.example.service.CartService;
import com.example.service.MemberService;
import com.example.service.OrderService;
import com.example.service.OrderitemService;

import jakarta.transaction.Transactional;

@RestController
public class OrderController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderitemService orderitemService;
	
	@Autowired
	private CartItemService cartitemService;
	
	//주문하기
	@PostMapping("/api/order/total/{id}")
	public ResponseEntity createOrder(@RequestBody OrderDTO dto) {
		String email = SecurityUtil.getCurrentEmail();
		Member member = memberService.findByEmail(email);
		Cart cart=cartService.findByMemberId(member.getId());
		//판매량 업데이
		for(int i=0;i<dto.getBooksWCount().size();i++) {
			Book book = bookService.findById(dto.getBooksWCount().get(i).getBookid());
			book.setHit(book.getHit()+dto.getBooksWCount().get(i).getCount());
			bookService.save(book);
		}
		
		//2. 주문번호 생성
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String ymd = ym +  new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String subNum = "";
		  
		for(int i = 1; i <= 6; i ++) {
			subNum += (int)(Math.random() * 10);
		}
		  
		String orderId = ymd + "_" + subNum; //ex) 20200508_373063
		
		//3. Order 생성
		Order createOrder = new Order();
		createOrder.setBuyer(dto.getBuyer());
		createOrder.setBuyerEmail(member.getEmail());
		createOrder.setBuyerAddress(dto.getBuyerAddress());
		createOrder.setStatus("주문완료");
		createOrder.setOrderNum(orderId);
		createOrder.setPayMethod(dto.getPayMethod());
		createOrder.setPg("html5_inicis");
		createOrder.setTotalPrice(dto.getTotalPrice());
		createOrder.setMember(member);
		orderService.save(createOrder);
		
		//4. Orderitem 생성
		for(int i=0;i<dto.getBooksWCount().size();i++) {
			OrderItem orderitem = new OrderItem();
			Book book = bookService.findById(dto.getBooksWCount().get(i).getBookid());
			orderitem.setBook(book);
			orderitem.setQuantity(dto.getBooksWCount().get(i).getCount());
			orderitem.setOrder(createOrder);
			orderitemService.save(orderitem);
		}
		
		//5. 장바구니에서 주문했을 경우, cartitem 삭제
		for(int i=0;i<dto.getBooksWCount().size();i++) {
			Book book = bookService.findById(dto.getBooksWCount().get(i).getBookid());
			CartItem cartitem = cartitemService.findByCartAndBook(cart, book);
			if(cartitem != null) {
				cartitemService.delete(cartitem);
			}
		}

		
		return new ResponseEntity<>(dto, HttpStatus.OK);
		
		
	}
	
	//주문목록(관리자용)
	@GetMapping("/api/get/order/all")
	public ResponseEntity AllOrderlist(@PageableDefault(size=10, sort = "orderDate", direction = Direction.DESC) 
									   Pageable pageable,
									   @RequestParam(defaultValue = "") String searchTerm) {
		Page<Order> orderlist = orderService.findByBuyerContainingOrStatusContainingOrOrderNumContaining(searchTerm, searchTerm, searchTerm, pageable);
		
		return new ResponseEntity<>(orderlist, HttpStatus.OK);
	}
	
	//주문상세
	@GetMapping("/api/get/order/detail/{id}/{orderid}")//memberid, orderid
	public ResponseEntity orderDetail(@PathVariable("id") int id, @PathVariable("orderid") int orderid) {
		Member member = memberService.findById(id);
		Order order = orderService.findById(orderid);
		List<OrderItem> orderitemlist = orderitemService.findAllByOrder(order);

		//주문 아이템 리스트
		List<BooksCountsDTO> dtoList = new ArrayList<>();
		for(int i=0;i<orderitemlist.size();i++) {
			Book book = orderitemlist.get(i).getBook();
			BooksCountsDTO dto = new BooksCountsDTO();
			dto.setBookid(book.getId());
			dto.setBookName(book.getBookName());
			dto.setAuthor(book.getAuthor());
			dto.setPublisher(book.getPublisher());
			dto.setCount(orderitemlist.get(i).getQuantity());
			dto.setTotal(book.getPrice()*dto.getCount());
			dtoList.add(dto);
		}
		
		//3. 상품 주문 총액 & 배송지 정보
		OrderResponseDTO resultDto = new OrderResponseDTO();
		resultDto.setBooksWCount(dtoList);
		resultDto.setMemberName(member.getName());
		resultDto.setBuyer(order.getBuyer());
		resultDto.setAddress(order.getBuyerAddress());
		resultDto.setPhone(member.getPhone());
		resultDto.setOrderNum(order.getOrderNum());
		resultDto.setOrderDate(order.getOrderDate());
		resultDto.setArrivalDate(order.getOrderDate().plusDays(1));
		resultDto.setStatus(order.getStatus());
		resultDto.setTotalprice(order.getTotalPrice());
		resultDto.setFinalprice(order.getTotalPrice());
		resultDto.setPayMethod(order.getPayMethod());
		
		
		return new ResponseEntity<>(resultDto, HttpStatus.OK);
	}
	
	//관리자용 주문상세
	@GetMapping("/api/get/order/detail/{orderid}")//memberid, orderid
	public ResponseEntity orderDetailAdmin(@PathVariable("orderid") int orderid) {
		
		Order order = orderService.findById(orderid);
		Member member = order.getMember();  
		List<OrderItem> orderitemlist = orderitemService.findAllByOrder(order);

		//주문 아이템 리스트
		List<BooksCountsDTO> dtoList = new ArrayList<>();
		for(int i=0;i<orderitemlist.size();i++) {
			Book book = orderitemlist.get(i).getBook();
			BooksCountsDTO dto = new BooksCountsDTO();
			dto.setBookid(book.getId());
			dto.setBookName(book.getBookName());
			dto.setAuthor(book.getAuthor());
			dto.setPublisher(book.getPublisher());
			dto.setCount(orderitemlist.get(i).getQuantity());
			dto.setTotal(book.getPrice()*dto.getCount());
			dtoList.add(dto);
		}
		
		//3. 상품 주문 총액 & 배송지 정보
		OrderResponseDTO resultDto = new OrderResponseDTO();
		resultDto.setBooksWCount(dtoList);
		resultDto.setMemberName(member.getName());
		resultDto.setBuyer(order.getBuyer());
		resultDto.setAddress(order.getBuyerAddress());
		resultDto.setPhone(member.getPhone());
		resultDto.setOrderNum(order.getOrderNum());
		resultDto.setOrderDate(order.getOrderDate());
		resultDto.setArrivalDate(order.getOrderDate().plusDays(1));
		resultDto.setStatus(order.getStatus());
		resultDto.setTotalprice(order.getTotalPrice());
		resultDto.setFinalprice(order.getTotalPrice());
		resultDto.setPayMethod(order.getPayMethod());
		
		
		return new ResponseEntity<>(resultDto, HttpStatus.OK);
	}
	
	//주문페이지 정보(장바구니에서)
	@GetMapping("/api/get/cart/wishorder/{id}/{ids}")//memberid, cartitemids
	public ResponseEntity CartToOrder(@PathVariable("id") int id, @PathVariable List<Integer> ids) {
		
		//1. 주문상품
		List<Integer> lists = ids.stream().filter(Objects::nonNull).collect(Collectors.toList());
		List<CartItem> cartitemlist = cartitemService.findAllByIdIn(lists);
		List<Book> books = cartitemlist.stream().map(CartItem::getBook).collect(Collectors.toList());
		List<BooksCountsDTO> dtoList = new ArrayList<>();
		for(int i=0;i<lists.size();i++) {
			int cartitemid = lists.get(i);
			CartItem cartitem = cartitemService.findById(cartitemid);
			Book book = cartitem.getBook();
			BooksCountsDTO dto = new BooksCountsDTO();
			dto.setBookid(book.getId());
			dto.setBookName(book.getBookName());
			dto.setAuthor(book.getAuthor());
			dto.setPublisher(book.getPublisher());
			dto.setCartitemid(cartitemid);
			dto.setCount(cartitem.getCount());
			dto.setTotal(book.getPrice()*cartitem.getCount());
			dtoList.add(dto);
		}
		
		//2. 배송지 정보
		Member member = memberService.findById(id);
		
		//3. 상품 주문 총액
		int totalprice = 0;
		
		for(CartItem list:cartitemlist) {
			Book book = list.getBook();
			totalprice += list.getCount()*list.getBook().getPrice();
		}
		
		ToOrderDTO resultDto = new ToOrderDTO();
		resultDto.setBooksWCount(dtoList);
		resultDto.setName(member.getName());
		resultDto.setBuyer(member.getName());
		resultDto.setAddress(member.getAddress());
		resultDto.setAddDetail(member.getAddDetail());
		resultDto.setPoint(member.getPoint());
		resultDto.setTotalprice(totalprice);
		resultDto.setPhone(member.getPhone());
		resultDto.setEmail(member.getEmail());
		
		return new ResponseEntity<>(resultDto, HttpStatus.OK);
	}
	
	//주문페이지 정보(목록에서)
		@GetMapping("/api/get/list/wishorder/{id}/{ids}")//memberid, bookids
		public ResponseEntity ListToOrder(@PathVariable("id") int id, @PathVariable List<Integer> ids) {
			
			//1. 주문상품
			List<Integer> lists = ids.stream().filter(Objects::nonNull).collect(Collectors.toList());
			List<Book> books = bookService.findAllByIdIn(lists);
			Member member = memberService.findById(id);
			
			List<BooksCountsDTO> dtoList = new ArrayList<>();
			for(int i=0;i<lists.size();i++) {
				int bookid = lists.get(i);
				Book book = bookService.findById(bookid);
				BooksCountsDTO dto = new BooksCountsDTO();
				dto.setBookid(book.getId());
				dto.setBookName(book.getBookName());
				dto.setAuthor(book.getAuthor());
				dto.setPublisher(book.getPublisher());
				dto.setCount(1);
				dto.setTotal(book.getPrice());
				dtoList.add(dto);
			}
			
			
			//2. 상품 주문 총액 & 배송지 정보
			int totalprice = 0;
			for(Book list:books) {
				totalprice += list.getPrice();
			}
			
			ToOrderDTO resultDto = new ToOrderDTO();
			resultDto.setBooksWCount(dtoList);
			resultDto.setName(member.getName());
			resultDto.setBuyer(member.getName());
			resultDto.setAddress(member.getAddress());
			resultDto.setAddDetail(member.getAddDetail());
			resultDto.setPoint(member.getPoint());
			resultDto.setTotalprice(totalprice);
			resultDto.setPhone(member.getPhone());
			resultDto.setEmail(member.getEmail());
			
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		}
		
		//주문페이지 정보(상세페이지에서)
		@GetMapping("/api/get/detail/wishorder/{id}/{bookid}/{count}")//memberid, bookid
		public ResponseEntity DetailToOrder(@PathVariable("id") int id, @PathVariable("bookid") int bookid, @PathVariable("count") int count) {
			
			//1. 주문상품
			Book book = bookService.findById(bookid);
			Member member = memberService.findById(id);
			
			List<BooksCountsDTO> dtoList = new ArrayList<>();
			for(int i=0;i<1;i++) {
				BooksCountsDTO dto = new BooksCountsDTO();
				dto.setBookid(book.getId());
				dto.setBookName(book.getBookName());
				dto.setAuthor(book.getAuthor());
				dto.setPublisher(book.getPublisher());
				dto.setCount(count);
				dto.setTotal(book.getPrice()*dto.getCount());
				dtoList.add(dto);
			}
			
			//3. 상품 주문 총액 & 배송지 정보
			
			ToOrderDTO resultDto = new ToOrderDTO();
			resultDto.setBooksWCount(dtoList);
			resultDto.setName(member.getName());
			resultDto.setBuyer(member.getName());
			resultDto.setAddress(member.getAddress());
			resultDto.setAddDetail(member.getAddDetail());
			resultDto.setPoint(member.getPoint());
			resultDto.setTotalprice(book.getPrice()*count);
			resultDto.setPhone(member.getPhone());
			
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		}
		
		//주문 정보 수정(관리자용)
		@Transactional
		@PutMapping("/api/admin/order/update/{id}")
		public ResponseEntity updateOrder(@PathVariable("id") int id, @RequestBody OrderUpdateDTO dto) {
			Order order = orderService.findById(id);
			
			order.setId(order.getId());
			order.setBuyer(order.getBuyer());
			order.setBuyerEmail(order.getBuyerEmail());
			order.setStatus(dto.getStatus());
			order.setOrderNum(order.getOrderNum());
			order.setOrderDate(order.getOrderDate());
			order.setPayMethod(order.getPayMethod());
			order.setPg(order.getPg());
			order.setTotalPrice(order.getTotalPrice());
			order.setMember(order.getMember());
			order.setOrderitems(order.getOrderitems());
			orderService.save(order);
			
			return new ResponseEntity<>(order,HttpStatus.OK);
		}

}



























