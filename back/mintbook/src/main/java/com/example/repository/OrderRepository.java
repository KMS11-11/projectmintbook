package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Member;
import com.example.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	

	List<Order> findByMember(Member findMember);

	List<Order> findByOrderNum(String num);

	List<Order> findByMemberId(String num);

	Page<Order> findByBuyerContainingOrStatusContainingOrOrderNumContaining(String searchTerm, String searchTerm2,
			String searchTerm3, Pageable pageable);

	

}
