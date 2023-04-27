package com.test.mo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 4000)
	private String content;
	
	private String writer;
	
	private LocalDateTime regDate;
	
	private Integer star;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JoinColumn(name = "writer_id")
	@JsonIgnore
	private Member member;
}
