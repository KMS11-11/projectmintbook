package com.test.co;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.mo.Cart;
import com.test.mo.Member;
import com.test.mo.DTO.LoginDTO;
import com.test.mo.DTO.MemberDTO;
import com.test.se.CartService;
import com.test.se.MemberService;

@RestController
public class MemberController {
	@Autowired
	private MemberService memS;
	
	@Autowired
	private CartService cartS;
	
	//create
	@PostMapping("/api/member/add")
	public ResponseEntity addMember(@RequestBody MemberDTO dto) {
		Member member=new Member();
		member.setEmail(dto.getEmail());
		member.setPassword(dto.getPassword());
		member.setGender(dto.getGender());
		member.setAddress(dto.getAddress());
		member.setAddDetail(dto.getAddDetail());
		member.setAge(dto.getAge());
		member.setName(dto.getName());
		member.setPhone(dto.getPhone());
		member.setBirth(dto.getBirth());
		member.setJoinDate(LocalDateTime.now());
		member.setIsAdmin(false);
		member.setPoint(0);
		memS.save(member);
		Cart cart=new Cart();
		cart.setMember(member);
		cartS.save(cart);
		return new ResponseEntity<>(member,HttpStatus.OK);
	}
	//read(login)
	@PostMapping("/api/member/login")
	public ResponseEntity login(@RequestBody LoginDTO logindto) {
		Member member=memS.findByEmailAndPassword(logindto);
		if(member != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//update
	@PatchMapping("/api/member/update/{id}")
	public ResponseEntity updateMember(@PathVariable("id")int id,@RequestBody MemberDTO dto) {
		Member member=memS.findById(id);
		member.setEmail(dto.getEmail());
		member.setPassword(dto.getPassword());
		member.setGender(dto.getGender());
		member.setAddress(dto.getAddress());
		member.setAddDetail(dto.getAddDetail());
		member.setAge(dto.getAge());
		member.setName(dto.getName());
		member.setPhone(dto.getPhone());
		member.setBirth(dto.getBirth());
		memS.save(member);
		return new ResponseEntity<>(member,HttpStatus.OK);
	}
	//delete
	@DeleteMapping("/api/member/delete/{id}")
	public HttpStatus deleteMember(@PathVariable("id")int id) {
		memS.deleteById(id);
		return HttpStatus.ACCEPTED;
	}
}
