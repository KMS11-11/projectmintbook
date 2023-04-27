package com.test.co;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mo.Book;
import com.test.mo.DTO.BookDTO;
import com.test.se.BookService;

@RestController
public class BookController {
	@Autowired
	private BookService bookS;
	
	//create
	@PostMapping("/api/book")
	public ResponseEntity addBook(@RequestBody BookDTO bookdto) {
		Book newbook=new Book();
		newbook.setBookName(bookdto.getBookName());
		newbook.setAuthor(bookdto.getAuthor());
		newbook.setPrice(bookdto.getPrice());
		newbook.setIsbn(bookdto.getIsbn());
		newbook.setGenre(bookdto.getGenre());
		newbook.setContent(bookdto.getContent());
		newbook.setPublisher(bookdto.getPublisher());
		newbook.setHit(0);
		newbook.setRegDate(LocalDateTime.now());
		bookS.save(newbook);
		return new ResponseEntity<>(newbook,HttpStatus.OK);
	}
	
	//read(one)
	@GetMapping("/api/book/{id}")
	public ResponseEntity findbookByid(@PathVariable("id")int id) {
		Book findbook=bookS.findById(id);
		if(findbook != null) {
			return new ResponseEntity<>(findbook,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//read(title)
	@GetMapping("/api/book/search/t")
	public ResponseEntity findbookByTitle(@RequestParam("title")String title) {
		List<Book> book=bookS.findByBookNameLike(title);
		return new ResponseEntity<>(book,HttpStatus.OK);
	}
	
	//read(genre)
	@GetMapping("/api/book/search/g")
	public ResponseEntity findbookByGenre(@RequestParam("genre")int genre) {
		List<Book> book=bookS.findByGenre(genre);
		return new ResponseEntity<>(book,HttpStatus.OK);
	}
	
	//read(isbn)
	@GetMapping("/api/book/search/i")
	public ResponseEntity findbookByIsbn(@RequestParam("isbn")String isbn) {
		Book book=bookS.findByIsbn(isbn);
		return new ResponseEntity<>(book,HttpStatus.OK);
	}
	
	//update
	@PatchMapping("/api/book/update/{id}")
	public ResponseEntity updateBook(@PathVariable("id")int id,@RequestBody BookDTO bookdto) {
		Book newbook=bookS.findById(id);
		newbook.setBookName(bookdto.getBookName());
		newbook.setAuthor(bookdto.getAuthor());
		newbook.setPrice(bookdto.getPrice());
		newbook.setIsbn(bookdto.getIsbn());
		newbook.setGenre(bookdto.getGenre());
		bookS.save(newbook);
		return new ResponseEntity<>(newbook,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/api/book/delete/{id}")
	public HttpStatus deleteBook(@PathVariable("id")int id) {
		bookS.deleteById(id);
		return HttpStatus.ACCEPTED;
	}
}
