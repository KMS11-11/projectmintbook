package com.test.re;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByBook_id(int id);

	List<Review> findByMember_id(int id);

	Review findByBook_idAndMember_id(Integer bid, Integer mid);

}
