package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.re.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventR;
}
