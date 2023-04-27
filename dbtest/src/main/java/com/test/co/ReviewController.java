package com.test.co;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mo.Book;
import com.test.mo.Member;
import com.test.mo.Review;
import com.test.mo.DTO.ReviewDTO;
import com.test.se.BookService;
import com.test.se.MemberService;
import com.test.se.ReviewService;

@RestController
public class ReviewController {
	@Autowired
	private ReviewService revS;
	
	@Autowired
	private BookService bookS;
	
	@Autowired
	private MemberService memS;
	
	//create
	@PostMapping("/api/review/add")
	public ResponseEntity addReview(@RequestBody ReviewDTO dto) {
		Review revt=revS.findByBook_idAndMember_id(dto.getBid(), dto.getMid());
		if(revt == null) {
			Review rev=new Review();
			Member mem=memS.findById(dto.getMid());
			Book book=bookS.findById(dto.getBid());
			rev.setContent(dto.getContent());
			rev.setWriter(dto.getWriter());
			rev.setStar(dto.getStar());
			rev.setRegDate(LocalDateTime.now());
			rev.setBook(book);
			rev.setMember(mem);
			revS.save(rev);
			return new ResponseEntity<>(rev,HttpStatus.OK);
		}
		return new ResponseEntity<>(revt,HttpStatus.OK);
	}
	
	//read(book)
	@GetMapping("/api/review/book")
	public ResponseEntity getReviewB(@RequestParam("id")int id) {
		List<Review> rev=revS.findByBook_id(id);
		return new ResponseEntity<>(rev,HttpStatus.OK);
	}
	
	@GetMapping("/api/review/book/star")
	public ResponseEntity getStar(@RequestParam("id")int id) {
		List<Review> reviews=revS.findByBook_id(id);
		int score=0;
		int star=0;
		float result=0;
		for(Review rev:reviews) {
			score += rev.getStar();
			star++;
		}
		if(star != 0) {
			result=score/star;
		}
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	//read(user)
	@GetMapping("/api/review/member")
	public ResponseEntity getReviewM(@RequestParam("id")int id) {
		List<Review> rev=revS.findByMember_id(id);
		return new ResponseEntity<>(rev,HttpStatus.OK);
	}
	
	//update
	@PatchMapping("/api/review/update")
	public ResponseEntity updateReview(@RequestBody ReviewDTO dto) {
		Review rev=revS.findByBook_idAndMember_id(dto.getBid(),dto.getMid());
		rev.setContent(dto.getContent());
		rev.setWriter(dto.getWriter());
		rev.setStar(dto.getStar());
		revS.save(rev);
		return new ResponseEntity<>(rev,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/api/review/delete")
	public ResponseEntity deleteReview(@RequestParam("id")int id) {
		Review rev=revS.findById(id);
		revS.delete(rev);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}




















