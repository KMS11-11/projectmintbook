package com.test.re;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

	List<Wishlist> findByMember_id(int id);

}
