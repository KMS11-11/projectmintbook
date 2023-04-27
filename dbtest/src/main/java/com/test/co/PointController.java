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
import com.test.mo.Point;
import com.test.mo.DTO.PointDTO;
import com.test.se.MemberService;
import com.test.se.PointService;

@RestController
public class PointController {
	@Autowired
	private PointService pointS;
	
	@Autowired
	private MemberService memS;
	
	//create
	@PostMapping("/api/point")
	public ResponseEntity changePoint(@RequestBody PointDTO dto) {
		Point point=new Point();
		Member mem=memS.findById(dto.getMid());
		point.setPrevPoint(mem.getPoint());
		point.setChangePoint(dto.getChange());
		point.setPresentPoint(mem.getPoint()+dto.getChange());
		point.setRegDate(LocalDateTime.now());
		point.setMember(mem);
		pointS.save(point);
		mem.setPoint(point.getPresentPoint());
		memS.save(mem);
		return new ResponseEntity<>(point,HttpStatus.OK);
	}
	
	//read
	@GetMapping("/api/point")
	public ResponseEntity logPoint(@RequestParam("id")int id) {
		List<Point> log=pointS.findByMember_id(id);
		return new ResponseEntity<>(log,HttpStatus.OK);
	}
}
