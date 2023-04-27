package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.CartItem;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	CartItem findByCart_idAndBook_id(Integer cid, Integer bid);
	
	@Transactional
	void deleteAllByCart_id(Integer id);

}
