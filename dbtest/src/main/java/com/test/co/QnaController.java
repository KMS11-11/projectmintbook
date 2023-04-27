package com.test.co;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mo.Member;
import com.test.mo.Qna;
import com.test.mo.QnaComment;
import com.test.mo.DTO.QnaCommentDTO;
import com.test.mo.DTO.QnaDTO;
import com.test.se.MemberService;
import com.test.se.QnaCommentService;
import com.test.se.QnaService;

@RestController
public class QnaController {
	@Autowired
	private QnaService qnaS;
	
	@Autowired
	private QnaCommentService qnaCS;
	
	@Autowired
	private MemberService memS;
	
	//create
	@PostMapping("/api/qna/write")
	public ResponseEntity writeQna(@RequestBody QnaDTO dto) {
		Qna q=new Qna();
		Member mem=memS.findById(dto.getMid());
		q.setContent(dto.getContent());
		q.setMember(mem);
		q.setRegDate(LocalDateTime.now());
		qnaS.save(q);
		return new ResponseEntity<>(q,HttpStatus.OK);
	}
	
	//read(all)
	@GetMapping("/api/qna/list")
	public ResponseEntity getQna(@RequestParam("id")int id) {
		List<Qna> qnalist=qnaS.findByMember_id(id);
		return new ResponseEntity<>(qnalist,HttpStatus.OK);
	}
	
	//read(one)
	@GetMapping("/api/qna/one")
	public ResponseEntity getQnaOne(@RequestParam("id")int id) {
		Qna q=qnaS.findById(id);
		return new ResponseEntity<>(q,HttpStatus.OK);
	}
	
	//create(reply)
	@PostMapping("/api/qna/reply")
	public ResponseEntity writeQnaReply(@RequestBody QnaCommentDTO dto) {
		QnaComment q=new QnaComment();
		Qna qna=qnaS.findById(dto.getQid());
		q.setContent(dto.getComment());
		q.setQna(qna);
		q.setRegDate(LocalDateTime.now());
		qnaCS.save(q);
		return new ResponseEntity<>(q,HttpStatus.OK);
	}
	
	//read
	@GetMapping("/api/qna/reply")
	public ResponseEntity getQnaReply(@RequestParam("id")int id) {
		QnaComment q=qnaCS.findByQna_id(id);
		return new ResponseEntity<>(q,HttpStatus.OK);
	}
}


























