package com.example.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.example.DTO.NoticeDTO;
import com.example.DTO.NoticeResponseDTO;
import com.example.domain.Notice;
import com.example.service.NoticeService;

@RestController
public class NoticeController {

	@Autowired NoticeService noticeService;
	
	//공지 목록
	@GetMapping("/api/get/notice")
	public ResponseEntity noticeList(@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<Notice> noticelist = noticeService.findAll(pageable);
		Page<NoticeResponseDTO> dto = new NoticeResponseDTO().toDtoList(noticelist);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	//공지 상세
	@GetMapping("/api/get/notice/detail/{id}")
	public ResponseEntity noticeDetail(@PathVariable("id") int id, NoticeResponseDTO dto) {
		
		Notice notice = noticeService.findById(id);
		
		return new ResponseEntity<>(notice, HttpStatus.OK);
	}
	
//	//메인 공지
//	@GetMapping("/api/get/main/notice")
//	public ResponseEntity mainNotice(@PageableDefault(size=1, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
//		
//		Page<Notice> noticelist = noticeService.findAll(pageable);
//		Page<NoticeResponseDTO> dto = new NoticeResponseDTO().toDtoList(noticelist);
//		
//		return new ResponseEntity<>(dto, HttpStatus.OK);
//	}
	
	//메인 공지
	@GetMapping("/api/get/main/notice")
	public ResponseEntity mainNotice() {
		
		Notice notice = noticeService.findTop1ByOrderByIdDesc();
		
		return new ResponseEntity<>(notice, HttpStatus.OK);
	}
	
	//공지사항 등록
	@PostMapping("/api/notice/write")
	public ResponseEntity noticeWrite(@RequestBody NoticeDTO params) {
		
		Notice newno = new Notice(); 
		newno.setTitle(params.getTitle());
		newno.setContent(params.getContent());
		newno.setWriter(params.getWriter());
		newno.setRegDate(LocalDate.now());
		noticeService.save(newno);
		
		return new ResponseEntity<>(newno, HttpStatus.OK);
	}
//	
//	//공지사항 리스트
//	@GetMapping("/noticelist")
//	public ResponseEntity<Page<Notice>> noticeList(@RequestParam("page") int page, @RequestParam("size") int size) {
//		
//		Pageable pageable = PageRequest.of(page, size);
//		
//		Page<Notice> noticeList = noticeService.findAll(pageable);
//		
//		return new ResponseEntity<Page<Notice>>(noticeList, HttpStatus.OK);
//	}
//	
//	//공지사항 상세보기
//	@GetMapping("/noticeone")
//	public ResponseEntity noticeOne(@RequestParam("id") int noticeid) {
//		
//		Notice noticeOne = noticeService.findById(noticeid);
//		
//		return new ResponseEntity<>(noticeOne, HttpStatus.OK);
//	}
//	
//	//공지사항 수정
//	@PutMapping("/revise/{id}")
//	public ResponseEntity noticeRevise(@PathVariable("id") int id, @RequestBody Notice params) {
//		
//		Notice notice = noticeService.findById(id);
//		
//		notice.setTitle(params.getTitle());
//		notice.setContent(params.getContent());
//		notice.setReviseDate(LocalDateTime.now());
//		
//		Notice result = noticeService.revise(notice);
//		
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//	
//	//공지사항 삭제
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity noticeDelete(@PathVariable("id")int id) {
//		
//		noticeService.deleteById(id);
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//	
//	//공지사항 검색
//	@GetMapping("/search")
//	public ResponseEntity noticeSearch(@RequestParam("subject") String subject) {
//		
//		List<Notice> result = noticeService.findByTitleContains(subject);
//		
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
}
