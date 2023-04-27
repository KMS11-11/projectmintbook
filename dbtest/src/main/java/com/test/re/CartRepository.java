package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	Cart findByMember_id(Integer mid);

}
