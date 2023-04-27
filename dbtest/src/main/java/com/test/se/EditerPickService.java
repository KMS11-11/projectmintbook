package com.test.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.EditerPick;
import com.test.re.EditerPickRepository;

@Service
public class EditerPickService {
	@Autowired
	private EditerPickRepository editR;

	public void save(EditerPick edit) {
		editR.save(edit);
	}
}
