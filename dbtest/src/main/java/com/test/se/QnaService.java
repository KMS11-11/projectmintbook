package com.test.se;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Qna;
import com.test.re.QnaRepository;

@Service
public class QnaService {
	@Autowired
	private QnaRepository qnaR;

	public void save(Qna q) {
		qnaR.save(q);
	}

	public List<Qna> findByMember_id(int id) {
		return qnaR.findByMember_id(id);
	}

	public Qna findById(int id) {
		return qnaR.findById(id).get();
	}
}
