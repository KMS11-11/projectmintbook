package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Member findByEmailAndPassword(String email, String password);


}
