package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	public void save(Order order) {
		orderRepository.save(order);
	}

	public Page<Order> findByBuyerContainingOrStatusContainingOrOrderNumContaining(String searchTerm, String searchTerm2, String searchTerm3, Pageable pageable) {
		return orderRepository.findByBuyerContainingOrStatusContainingOrOrderNumContaining(searchTerm,searchTerm2,searchTerm3,pageable);
	}

	public Order findById(int orderid) {
		return orderRepository.findById(orderid).get();
	}
}
