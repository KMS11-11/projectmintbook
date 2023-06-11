package com.example.DTO;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.example.domain.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDTO {
	private int id;
	private String title;
	private String content;
	private LocalDate regDate;
	
	public Page<NoticeResponseDTO> toDtoList(Page<Notice> noticelist) {
		Page<NoticeResponseDTO> noticeDtoList = noticelist.map(m -> 
												NoticeResponseDTO.builder()
												.id(m.getId())
												.title(m.getTitle())
												.content(m.getContent())
												.regDate(m.getRegDate())
												.build());
		return noticeDtoList;
	}
}
