package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	//카트아이템 저장
	void save(Cart newcart);

	//카트아이템에 데이터 조회
	CartItem findByCartIdAndBookId(Integer cartid, Integer bookid);

	CartItem findByCartAndBook(Cart cart, Book book);

	List<CartItem> findAllByIdIn(List<Integer> lists);

//	Page<CartItem> findAllByCartId(Integer id, Pageable pageable);
	List<CartItem> findAllByCartId(Integer id);
	
}
