package com.example.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
	private String title;
	private String listimgPath;
	private String imgPath;
	private LocalDate startDate;
	private LocalDate endDate;
}
