package com.test.se;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Wishlist;
import com.test.re.WishlistRepository;

@Service
public class WishlistService {
	@Autowired
	private WishlistRepository wishR;

	public void save(Wishlist wish) {
		wishR.save(wish);
	}

	public List<Wishlist> findByMember_id(int id) {
		return wishR.findByMember_id(id);
	}

	public Wishlist findById(int id) {
		return wishR.findById(id).get();
	}

	public void delete(Wishlist wish) {
		wishR.delete(wish);
	}
}
