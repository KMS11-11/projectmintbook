package com.test.co;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mo.Book;
import com.test.mo.Cart;
import com.test.mo.CartItem;
import com.test.mo.Member;
import com.test.mo.Order;
import com.test.mo.OrderItem;
import com.test.mo.DTO.OrderDTO;
import com.test.mo.DTO.OrderDetailDTO;
import com.test.se.BookService;
import com.test.se.CartService;
import com.test.se.MemberService;
import com.test.se.OrderItemService;
import com.test.se.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService ordS;
	
	@Autowired
	private OrderItemService ordIS;
	
	@Autowired
	private MemberService memS;
	
	@Autowired
	private CartService cartS;
	
	@Autowired
	private BookService bookS;
	
	//create
	@PostMapping("/api/order")
	public ResponseEntity addOrder(@RequestBody OrderDTO dto) {
		Order order=new Order();
		Cart cart=cartS.findByMember_id(dto.getMid());
		Member mem=memS.findById(dto.getMid());
		List<CartItem> carti=cart.getCartitems();
		int totalprice=0;
		for(CartItem cartitem:carti) {
			Book book=cartitem.getBook();
			totalprice += cartitem.getQuantity()*cartitem.getBook().getPrice();
			book.setHit(book.getHit()+cartitem.getQuantity());
			bookS.save(book);
		}
		order.setBuyer(dto.getBuyer());
		order.setBuyerAddress(dto.getBuyerAddress());
		order.setBuyerEmail(dto.getBuyerEmail());
		order.setPrice(totalprice);
		order.setStatus("ordered");
		order.setMember(mem);
		long seed=System.currentTimeMillis();
		Random rand=new Random(seed);
		StringBuffer code=new StringBuffer();
		for(int i=0;i<10;i++) {
			if(rand.nextBoolean()) {
				code.append((char)((int)(rand.nextInt(26))+65));
			}
			else {
				code.append((rand.nextInt(10)));
			}
		}
		String time=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		order.setOrderNum(time+code);
		order.setOrderDate(LocalDateTime.now());
		ordS.save(order);
		for(CartItem cartitem:carti) {
			OrderItem item=new OrderItem();
			item.setBook(cartitem.getBook());
			item.setQuantity(cartitem.getQuantity());
			item.setOrder(order);
			ordIS.save(item);
		}
		cartS.delete(cart);
		Cart newcart=new Cart();
		newcart.setMember(mem);
		cartS.save(newcart);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PostMapping("/api/order/direct")
	public ResponseEntity directOrder(@RequestBody OrderDTO dto) {
		Order order=new Order();
		Member mem=memS.findById(dto.getMid());
		Book book=bookS.findById(dto.getBid());
		book.setHit(book.getHit()+dto.getQuantity());
		int total=book.getPrice()*dto.getQuantity();
		order.setBuyer(dto.getBuyer());
		order.setBuyerAddress(dto.getBuyerAddress());
		order.setBuyerEmail(dto.getBuyerEmail());
		order.setPrice(total);
		order.setStatus("ordered");
		order.setMember(mem);
		long seed=System.currentTimeMillis();
		Random rand=new Random(seed);
		StringBuffer code=new StringBuffer();
		for(int i=0;i<10;i++) {
			if(rand.nextBoolean()) {
				code.append((char)((int)(rand.nextInt(26))+65));
			}
			else {
				code.append((rand.nextInt(10)));
			}
		}
		String time=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		order.setOrderNum(time+code);
		order.setOrderDate(LocalDateTime.now());
		ordS.save(order);
		OrderItem item=new OrderItem();
		item.setBook(book);
		item.setOrder(order);
		item.setQuantity(dto.getQuantity());
		ordIS.save(item);
		bookS.save(book);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	//read
	@GetMapping("/api/order/all")
	public ResponseEntity getAllOrder(@RequestParam("id")int id) {
		List<Order> orders=ordS.findByMember_id(id);
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/api/order/detail")
	public ResponseEntity getOneOrder(@RequestParam("id")String id) {
		Order order=ordS.findByOrderNum(id);
		List<OrderItem> items=order.getOrderitems();
		OrderDetailDTO dto=new OrderDetailDTO();
		dto.setOrder(order);
		dto.setOrderitems(items);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
}
