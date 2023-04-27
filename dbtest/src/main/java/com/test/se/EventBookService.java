package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.re.EventBookRepository;

@Service
public class EventBookService {
	@Autowired
	private EventBookRepository eventR;
}
