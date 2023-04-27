package com.test.co;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.mo.Book;
import com.test.mo.EditerPick;
import com.test.mo.DTO.EditerPickDTO;
import com.test.se.BookService;
import com.test.se.EditerPickService;

@RestController
public class EditerPickController {
	@Autowired
	private EditerPickService editS;
	
	@Autowired
	private BookService bookS;
	
	//create
	@PostMapping("/api/editer/add")
	public ResponseEntity addEditpick(@RequestBody EditerPickDTO dto) {
		EditerPick edit=new EditerPick();
		Book book=bookS.findById(dto.getBid());
		edit.setBook(book);
		edit.setContent(dto.getContent());
		edit.setTitle(dto.getTitle());
		edit.setRegDate(LocalDateTime.now());
		editS.save(edit);
		return new ResponseEntity<>(edit,HttpStatus.OK);
	}
}
