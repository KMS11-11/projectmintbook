package com.test.mo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 255)
	private String bookName;
	
	@Column(length = 100)
	private String author;
	
	private Integer price;
	
	private LocalDateTime regDate;
	
	@Column(length = 255,unique = true)
	private String isbn;
	
	private Integer hit;
	
	private Integer genre;
	
	private String content;
	
	private String publisher;
	
	@JsonBackReference
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Review> reviews=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EditerPick> editers=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EventBook> eventbooks=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Wishlist> wishlists=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CartItem> cartitems=new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<OrderItem> orderitems=new ArrayList<>();
}
