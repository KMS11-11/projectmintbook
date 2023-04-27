package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.QnaComment;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Integer> {

	QnaComment findByQna_id(int id);

}
