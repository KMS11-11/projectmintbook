package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Member;
import com.test.mo.DTO.LoginDTO;
import com.test.re.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memR;

	public void save(Member member) {
		memR.save(member);
	}

	public Member findById(int id) {
		return memR.findById(id).get();
	}

	public void deleteById(int id) {
		memR.deleteById(id);
	}

	public Member findByEmailAndPassword(LoginDTO logindto) {
		return memR.findByEmailAndPassword(logindto.getEmail(),logindto.getPassword());
	}
}
