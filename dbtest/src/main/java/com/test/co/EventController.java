package com.test.co;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.se.BookService;
import com.test.se.EventBookService;
import com.test.se.EventService;

@RestController
public class EventController {
	@Autowired
	private EventService eventS;
	
	@Autowired
	private EventBookService eventBS;
	
	@Autowired
	private BookService bookS;
}
