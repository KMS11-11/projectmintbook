package com.test.co;

import java.util.List;

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
import com.test.mo.DTO.CartAddDTO;
import com.test.se.BookService;
import com.test.se.CartItemService;
import com.test.se.CartService;

@RestController
public class CartController {
	@Autowired
	private CartService cartS;
	
	@Autowired
	private CartItemService cartSi;
	
	@Autowired
	private BookService bookS;
	
	//add
	@PostMapping("/api/cart/add")
	public ResponseEntity addCart(@RequestBody CartAddDTO dto) {
		Cart cart=cartS.findByMember_id(dto.getMid());
		CartItem carti=cartSi.findByCart_idAndBook_id(cart.getId(),dto.getBid());
		Book book=bookS.findById(dto.getBid());
		if(carti == null) {
			CartItem newitem=new CartItem();
			newitem.setBook(book);
			newitem.setCart(cart);
			newitem.setQuantity(1);
			cartSi.save(newitem);
			return new ResponseEntity<>(newitem,HttpStatus.OK);
		}
		carti.setQuantity(carti.getQuantity()+1);
		cartSi.save(carti);
		return new ResponseEntity<>(carti,HttpStatus.OK);
	}
	
	//read
	@GetMapping("/api/cart/get")
	public ResponseEntity getCart(@RequestParam("id")int id) {
		Cart cart=cartS.findByMember_id(id);
		if(cart != null) {
			List<CartItem> cartitems=cart.getCartitems();
			return new ResponseEntity<>(cartitems,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//update/delete will be another place
}




















