package com.test.mo.DTO;

import lombok.Data;

@Data
public class ReviewDTO {
	private String content;
	private String writer;
	private Integer star;
	private Integer bid;
	private Integer mid;
}
