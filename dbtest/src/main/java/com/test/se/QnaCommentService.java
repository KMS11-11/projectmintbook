package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.QnaComment;
import com.test.re.QnaCommentRepository;

@Service
public class QnaCommentService {
	@Autowired
	private QnaCommentRepository qnaCR;

	public void save(QnaComment q) {
		qnaCR.save(q);
	}

	public QnaComment findByQna_id(int id) {
		return qnaCR.findByQna_id(id);
	}
}
