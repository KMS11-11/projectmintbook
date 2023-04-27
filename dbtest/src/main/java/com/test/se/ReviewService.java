package com.test.se;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Review;
import com.test.re.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository revR;

	public void save(Review rev) {
		revR.save(rev);
	}

	public List<Review> findByBook_id(int id) {
		return revR.findByBook_id(id);
	}

	public List<Review> findByMember_id(int id) {
		return revR.findByMember_id(id);
	}

	public Review findByBook_idAndMember_id(Integer bid, Integer mid) {
		return revR.findByBook_idAndMember_id(bid,mid);
	}

	public Review findById(int id) {
		return revR.findById(id).get();
	}

	public void deleteById(int id) {
		revR.deleteById(id);
	}

	public void delete(Review rev) {
		revR.delete(rev);
	}
}
