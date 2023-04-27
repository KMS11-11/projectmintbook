package com.test.mo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100,unique = true)
	private String email;
	
	@Column(length = 255)
	@JsonIgnore
	private String password;
	
	@Column(length = 10)
	private String gender;
	
	@Column(length = 255)
	private String address;
	
	@Column(length = 255)
	private String addDetail;
	
	private Integer age;
	
	@Column(length = 255)
	private String name;
	
	@Column(length = 20)
	private String phone;
	
	private Date birth;
	private LocalDateTime joinDate;
	private Boolean isAdmin;
	private Integer point;
	
	@JsonBackReference
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Review> reviews=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Wishlist> wishlists=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Cart> carts=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Order> orders=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Point> points=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Qna> qnas=new ArrayList<>();
}
