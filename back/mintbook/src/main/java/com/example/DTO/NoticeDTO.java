package com.example.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NoticeDTO {
	private String title;
	private String content;
	private String writer;
	private LocalDate regDate;
	private LocalDate reviseDate;
}
