package com.test.re;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
