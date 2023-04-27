package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
