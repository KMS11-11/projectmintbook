package com.test.se;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Order;
import com.test.re.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository ordR;

	public void save(Order order) {
		ordR.save(order);
	}

	public List<Order> findByMember_id(int id) {
		return ordR.findByMember_id(id);
	}

	public Order findByOrderNum(String id) {
		return ordR.findByOrderNum(id);
	}
}
