package com.test.co;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mo.Book;
import com.test.mo.Member;
import com.test.mo.Wishlist;
import com.test.mo.DTO.WishDTO;
import com.test.se.BookService;
import com.test.se.MemberService;
import com.test.se.WishlistService;

@RestController
public class WishlistController {
	@Autowired
	private WishlistService wishS;
	
	@Autowired
	private BookService bookS;
	
	@Autowired
	private MemberService memS;
	
	//create
	@PostMapping("/api/wish/add")
	public ResponseEntity addWish(@RequestBody WishDTO dto) {
		Wishlist wish=new Wishlist();
		Book book=bookS.findById(dto.getBid());
		Member mem=memS.findById(dto.getMid());
		wish.setBook(book);
		wish.setMember(mem);
		wishS.save(wish);
		return new ResponseEntity<>(wish,HttpStatus.OK);
	}
	
	//read
	@GetMapping("/api/wish/list")
	public ResponseEntity listWish(@RequestParam("id")int id) {
		List<Wishlist> wish=wishS.findByMember_id(id);
		return new ResponseEntity<>(wish,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/api/wish/delete")
	public HttpStatus deleteWish(@RequestParam("id")int id) {
		Wishlist wish=wishS.findById(id);
		wishS.delete(wish);
		return HttpStatus.ACCEPTED;
	}
}





















