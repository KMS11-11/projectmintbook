package com.test.re;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Point;

public interface PointRepository extends JpaRepository<Point, Integer> {

	List<Point> findByMember_id(int id);

}
