package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

}
