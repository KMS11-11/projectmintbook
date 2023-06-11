package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;
import com.example.repository.CartItemRepository;

@Service
public class CartItemService {

	@Autowired CartItemRepository cartItemRepository;

	public CartItem findByCartIdAndBookId(Integer cartid, Integer  bookid) {
		return cartItemRepository.findByCartIdAndBookId(cartid, bookid);
	}

	public void save(CartItem newitem) {
		cartItemRepository.save(newitem);
	}

	public CartItem findByCartAndBook(Cart cart, Book book) {
		return cartItemRepository.findByCartAndBook(cart,book);
	}

	public void delete(CartItem cartitem) {
		cartItemRepository.delete(cartitem);
	}

	public List<CartItem> findAllByIdIn(List<Integer> lists) {
		return cartItemRepository.findAllByIdIn(lists);
	}

	public CartItem findById(int id) {
		return cartItemRepository.findById(id).get();
	}

//	public Page<CartItem> findAllByCartId(Integer id, Pageable pageable) {
//		return cartItemRepository.findAllByCartId(id,pageable);
//	}
	public List<CartItem> findAllByCartId(Integer id) {
		return cartItemRepository.findAllByCartId(id);
	}

	public void deleteAllByIdIn(List<Integer> lists) {
		lists.forEach(id -> cartItemRepository.deleteById(id));
	}
}
