package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

	Page<Event> findByTitleContaining(String searchTerm, Pageable pageable);

}
