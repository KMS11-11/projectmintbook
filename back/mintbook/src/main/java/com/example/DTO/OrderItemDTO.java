package com.example.DTO;

import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.Member;

import lombok.Data;

@Data
public class OrderItemDTO {
	private int cartitemid;
	private int quantity;
	private int total;
	private Book book;
	private Cart cart;
	private Member member;
}
