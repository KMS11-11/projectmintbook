package com.test.mo.DTO;

import lombok.Data;

@Data
public class BookDTO {
	private String bookName;
	private String author;
	private Integer price;
	private String isbn;
	private Integer genre;
	private String content;
	private String publisher;
}
