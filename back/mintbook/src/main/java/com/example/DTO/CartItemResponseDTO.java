package com.example.DTO;

import org.springframework.data.domain.Page;

import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
	private int id;
	private Cart cart;
	private Book book;
	private int count;
	private int quantity;
	
	public CartItemResponseDTO(CartItem entity) {
		this.id = entity.getId();
		this.cart = entity.getCart();
		this.book = entity.getBook();
		this.count = entity.getCount();
		this.quantity = entity.getQuantity();
	}

	public Page<CartItemResponseDTO> toDtoList(Page<CartItem> cartitems) {
		Page<CartItemResponseDTO> cartitemDtoList = cartitems.map(m-> 
		CartItemResponseDTO.builder()
						.id(m.getId())
						.cart(m.getCart())
						.book(m.getBook())
						.count(m.getCount())
						.quantity(m.getQuantity())
						.build());
		return cartitemDtoList;
	}
}
