package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.CartItem;
import com.test.re.CartItemRepository;

@Service
public class CartItemService {
	@Autowired
	private CartItemRepository cartiR;

	public void save(CartItem carti) {
		cartiR.save(carti);
	}

	public CartItem findByCart_idAndBook_id(Integer cid, Integer bid) {
		return cartiR.findByCart_idAndBook_id(cid,bid);
	}

	public void delete(CartItem cartitem) {
		cartiR.delete(cartitem);
	}

	public void deleteAllByCart_id(Integer id) {
		cartiR.deleteAllByCart_id(id);
	}
}
