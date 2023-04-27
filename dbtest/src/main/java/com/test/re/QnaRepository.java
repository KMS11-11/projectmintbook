package com.test.re;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Qna;

public interface QnaRepository extends JpaRepository<Qna, Integer> {

	List<Qna> findByMember_id(int id);

}
