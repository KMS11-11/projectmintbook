package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.example.DTO.CartAddDto;
import com.example.DTO.CartItemResponseDTO;
import com.example.DTO.OrderItemDTO;
import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;
import com.example.domain.Member;
import com.example.security.SecurityUtil;
import com.example.service.BookService;
import com.example.service.CartItemService;
import com.example.service.CartService;
import com.example.service.MemberService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired CartService cartService;
	
	@Autowired MemberService memberService;
	
	@Autowired CartItemService cartItemService;
	
	@Autowired BookService bookService;
	
	//카트 추가
	@PostMapping("/cart")
	public ResponseEntity addCart(@RequestParam("bookid") int bookids) {
		
		String email = SecurityUtil.getCurrentEmail();
		Member member = memberService.findByEmail(email);
		
		Cart cart = cartService.findByMemberId(member.getId());
		
		//카트 조회시 카트가 null이면 카트부터 저장해준다.
		if(cart == null) {
			Cart newcart = new Cart();
			newcart.setMember(member);
			
			cartService.save(newcart);
			cart = cartService.findByMemberId(member.getId());
		}
		
//		//배열로 받아온 bookid값을 for문으로 꺼내서 저장한다.
//	    for (int i = 0; i < bookids.size(); i++) {
//	        Integer bookid = bookids.get(i);
//	        CartItem cartitem = cartItemService.findByCartIdAndBookId(cart.getId(),bookid);
//	        Book book = bookService.findById(bookid);
//	        
//	      //카트아이템이 없으면 새로 저장
//			if(cartitem == null) {
//				CartItem newitem = new CartItem();
//				newitem.setBook(book);
//				newitem.setCart(cart);
//				newitem.setQuantity(1);
//				cartItemService.save(newitem);
//			} else {
//				//기존등록정보가 있으면 기존 수량 +1
//				cartitem.setQuantity(cartitem.getQuantity()+1);
//				cartItemService.save(cartitem);
//			}
//	    }
        CartItem cartitem = cartItemService.findByCartIdAndBookId(cart.getId(),bookids);
        Book book = bookService.findById(bookids);
        
      //카트아이템이 없으면 새로 저장
		if(cartitem == null) {
			CartItem newitem = new CartItem();
			newitem.setBook(book);
			newitem.setCart(cart);
			newitem.setQuantity(1);
			newitem.setCount(0);
			cartItemService.save(newitem);
		} else {
			//기존등록정보가 있으면 기존 수량 +1
			cartitem.setQuantity(cartitem.getQuantity()+1);
			cartItemService.save(cartitem);
		}

		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//장바구니 목록
	@GetMapping("/get/cartitem")
	public ResponseEntity CartitemList(@PageableDefault(size=10, 
								       					sort = "id", 
						       							direction = Sort.Direction.DESC) 
														Pageable pageable){
		String email = SecurityUtil.getCurrentEmail();
		Member member = memberService.findByEmail(email);
		Cart cart = cartService.findByMemberId(member.getId());//cartid
		
//		Page<CartItem> cartitems = cartItemService.findAllByCartId(cart.getId(), pageable);
		List<CartItem> cartitems = cartItemService.findAllByCartId(cart.getId());
//		List<Map<Integer,Book>> lists=new ArrayList<Map<Integer,Book>>();
//		List<Book> books=new ArrayList<Book>();
//		for(int i=0;i<cartitems.size();i++) {
//			Map<Integer,Book> ins=new HashMap<Integer,Book>();
//			ins.put(cartitems.get(i).getId(),cartitems.get(i).getBook());
//			lists.add(ins);
//		}
		List<CartItemResponseDTO> lists=new ArrayList<CartItemResponseDTO>();

		for(int i=0;i<cartitems.size();i++) {
			CartItemResponseDTO dto=new CartItemResponseDTO();
			dto.setBook(cartitems.get(i).getBook());
			dto.setId(cartitems.get(i).getId());
			dto.setCount(cartitems.get(i).getCount());
			dto.setQuantity(cartitems.get(i).getQuantity());
			dto.setCart(cartitems.get(i).getCart());
			lists.add(dto);
		}

//		Page<CartItemResponseDTO> dto = new CartItemResponseDTO().toDtoList(cartitems);
		
		return new ResponseEntity<>(lists, HttpStatus.OK);
	}
	
	//장바구니 주문
	@GetMapping("/get/cartorder")
	public ResponseEntity orderitemList(){
		String email = SecurityUtil.getCurrentEmail();
		Member member = memberService.findByEmail(email);
		Cart cart = cartService.findByMemberId(member.getId());//cartid
		
		List<CartItem> cartitems = cartItemService.findAllByCartId(cart.getId());

		List<OrderItemDTO> lists=new ArrayList<>();

		for(int i=0;i<cartitems.size();i++) {
			OrderItemDTO dto=new OrderItemDTO();
			int totals=cartitems.get(i).getBook().getPrice()*cartitems.get(i).getQuantity();
			dto.setBook(cartitems.get(i).getBook());
			dto.setCartitemid(cartitems.get(i).getId());
			dto.setTotal(totals);
			dto.setQuantity(cartitems.get(i).getQuantity());
			dto.setCart(cartitems.get(i).getCart());
			dto.setMember(member);
			lists.add(dto);
		}

//		Page<CartItemResponseDTO> dto = new CartItemResponseDTO().toDtoList(cartitems);
		
		return new ResponseEntity<>(lists, HttpStatus.OK);
	}
	
	
	
	//장바구니 아이템 삭제(리스트)
	@Transactional
	@DeleteMapping("/api/delete/cartitem/{ids}")
	List<Integer> deleteCartitems(@PathVariable List<Integer> ids){
		// ids입력 > int형 리스트로 전환 > 리스트에 해당하는 cartitem 호출
		// dto로 ids 입력 > int형
		List<Integer> lists = ids.stream().filter(Objects::nonNull).collect(Collectors.toList());
		
		cartItemService.deleteAllByIdIn(lists);
		
		return lists;
	}
}
