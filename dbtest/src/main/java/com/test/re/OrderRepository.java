package com.test.re;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByMember_id(int id);

	Order findByOrderNum(String id);

}
