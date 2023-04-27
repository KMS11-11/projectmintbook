package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.OrderItem;
import com.test.re.OrderItemRepository;

@Service
public class OrderItemService {
	@Autowired
	private OrderItemRepository ordR;

	public void save(OrderItem item) {
		ordR.save(item);
	}
}
