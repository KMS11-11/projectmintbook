package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.EventBook;

public interface EventBookRepository extends JpaRepository<EventBook, Integer> {

}
