package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.example.DTO.EventRequestDTO;
import com.example.DTO.EventResponseDTO;
import com.example.domain.Event;
import com.example.service.EventService;

import jakarta.transaction.Transactional;

@RestController
public class EventController {
	@Autowired
	private EventService eventService;
	
	//이벤트 등록
	@PostMapping("/api/add/event")
	public ResponseEntity addEvent(@RequestBody EventRequestDTO dto) {
		
		Event addE = new Event();
		
		addE.setTitle(dto.getTitle());
		addE.setImgPath(dto.getImgPath());
		addE.setListimgPath(dto.getListimgPath());
		addE.setStartDate(dto.getStartDate());
		addE.setEndDate(dto.getEndDate());
		
		Event result = eventService.save(addE);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//이벤트 목록(유저용)
	@GetMapping("/api/get/event")
	public ResponseEntity EventList() {
		
		List<Event> lists = eventService.findAll();
		List<EventResponseDTO> dtolists = new ArrayList<>();
		
		for(Event list:lists) {
			EventResponseDTO rsdto = new EventResponseDTO();
			
			rsdto.setId(list.getId());
			rsdto.setTitle(list.getTitle());
			rsdto.setImgPath(list.getImgPath());
			rsdto.setListimgPath(list.getListimgPath());
			rsdto.setRegDate(list.getRegDate());
			rsdto.setStartDate(list.getStartDate());
			rsdto.setEndDate(list.getEndDate());
			
			dtolists.add(rsdto);
		}
		return new ResponseEntity<>(dtolists, HttpStatus.OK);
	}
	
	//이벤트 검색
	@GetMapping("/api/admin/event/search")
	public ResponseEntity EventListPage(@PageableDefault(size=10, sort="regDate", direction = Sort.Direction.DESC)
										Pageable pageable,
										@RequestParam(defaultValue = "") String searchTerm) {
		
		Page<Event> eventList = eventService.findByTitleContaining(searchTerm, pageable);
		
		Page<EventResponseDTO> eventDto = new EventResponseDTO().eventtoDto(eventList);
		
		return new ResponseEntity<>(eventDto, HttpStatus.OK);
	}
	
	//이벤트 1개 정보
	@GetMapping("/api/get/event/detail")
	public ResponseEntity eventDetail(@RequestParam("id") int id) {
		Event event = eventService.findById(id);
		
		return new ResponseEntity<>(event, HttpStatus.OK);
	}
	
	//이벤트 수정
	@Transactional
	@PutMapping("/api/event/edit")
	public ResponseEntity editEvent(@RequestParam("id") int id, @RequestBody EventRequestDTO dto) {
		
		Event event = eventService.findById(id);
		
		Event result = eventService.update(id, dto.getImgPath(),
											   dto.getListimgPath(),
											   dto.getTitle(),
											   dto.getStartDate(),
											   dto.getEndDate());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//이벤트 삭제
	@DeleteMapping("/api/event/delete")
	public ResponseEntity deleteEvent(@RequestParam("id") int id) {
		
		eventService.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}



































