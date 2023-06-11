package com.example.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 500)
	private String title;
	
	@Column(length = 4000)
	private String listimgPath;
	
	@Column(length = 4000)
	private String imgPath;
	
	private LocalDateTime regDate = LocalDateTime.now();
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EventBook> eventbooks;

	public Event update(String imgPath, String listimgPath, String title, LocalDate startDate, LocalDate endDate) {
		this.imgPath = imgPath;
		this.listimgPath = listimgPath;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		return this;
	}
}
