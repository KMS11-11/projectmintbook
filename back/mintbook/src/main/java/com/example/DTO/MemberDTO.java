package com.example.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private LocalDate birth;
	private String snsType;
	private String snsId;
	private String snsProfile;
	private LocalDateTime snsConnectDate;
}
