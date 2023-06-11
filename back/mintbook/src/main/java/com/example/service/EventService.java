package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.domain.Event;
import com.example.repository.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;

	public Event save(Event addE) {
		return eventRepository.save(addE);
	}

	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	public Page<Event> findByTitleContaining(String searchTerm, Pageable pageable) {
		return eventRepository.findByTitleContaining(searchTerm, pageable);
	}

	public Event findById(int id) {
		return eventRepository.findById(id).get();
	}

	public Event update(int id, String imgPath, String listimgPath, String title, LocalDate startDate,
			LocalDate endDate) {
		Event event = eventRepository.findById(id).get();
		Event result = event.update(imgPath, listimgPath, title, startDate, endDate);
		return result;
	}

	public void deleteById(int id) {
		eventRepository.deleteById(id);
	}

}
