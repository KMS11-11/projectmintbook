package com.test.mo.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberDTO {
	private String email;
	private String password;
	private String gender;
	private String address;
	private String addDetail;
	private Integer age;
	private String name;
	private String phone;
	private Date birth;
}
