package com.test.mo.DTO;

import java.util.List;

import com.test.mo.Order;
import com.test.mo.OrderItem;

import lombok.Data;

@Data
public class OrderDetailDTO {
	private Order order;
	private List<OrderItem> orderitems;
}
