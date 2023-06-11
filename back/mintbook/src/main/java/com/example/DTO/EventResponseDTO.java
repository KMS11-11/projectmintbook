package com.example.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.example.domain.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
	private int id;
	private String title;
	private String listimgPath;
	private String imgPath;
	private LocalDateTime regDate;
	private LocalDate startDate;
	private LocalDate endDate;
	
	
	public Page<EventResponseDTO> eventtoDto(Page<Event> eventList) {
		Page<EventResponseDTO> eventDtoList = eventList.map(m->
							EventResponseDTO.builder()
							.id(m.getId())
							.title(m.getTitle())
							.listimgPath(m.getListimgPath())
							.imgPath(m.getImgPath())
							.regDate(m.getRegDate())
							.startDate(m.getStartDate())
							.endDate(m.getEndDate())
							.build());
		return eventDtoList;
	}
}
