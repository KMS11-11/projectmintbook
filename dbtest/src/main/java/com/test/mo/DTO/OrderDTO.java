package com.test.mo.DTO;

import lombok.Data;

@Data
public class OrderDTO {
	private String buyer;
	private String buyerAddress;
	private String buyerEmail;
	private Integer mid;
	
	//for direct order
	private Integer bid;
	private Integer quantity;
}
