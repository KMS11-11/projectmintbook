package com.test.se;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Point;
import com.test.re.PointRepository;

@Service
public class PointService {
	@Autowired
	private PointRepository pointR;

	public void save(Point point) {
		pointR.save(point);
	}

	public List<Point> findByMember_id(int id) {
		return pointR.findByMember_id(id);
	}
}
