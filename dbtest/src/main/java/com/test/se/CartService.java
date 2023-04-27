package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Cart;
import com.test.re.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartRepository cartR;

	public void save(Cart cart) {
		cartR.save(cart);
	}

	public Cart findByMember_id(Integer mid) {
		return cartR.findByMember_id(mid);
	}

	public void delete(Cart cart) {
		cartR.delete(cart);
	}
}
