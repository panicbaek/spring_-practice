package com.example.board.domain;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	private final int BLOCK_SIZE = 10; // 블록당 표시할 페이지번호 개수
	
	private int startPage; // 해당 블록에 시작 번호
	private int endPage; // 해당 블록에 끝 번호
	private boolean prev, next; // 이전, 다음 블록이 있는지 여부
	
	private int totalPages; // 전체 페이지 수
	private long totalElements; //  전체 레코드 수
	private Page<Post> page; // 페이지 객체
	
	public PageDTO(Page<Post> page) {
		this.page = page;
		this.totalPages = page.getTotalPages();
		this.totalElements = page.getTotalElements();
		
		// 현재 페이지
		int currentPage = page.getNumber() + 1;
		// 끝번호
		this.endPage = (int)(Math.ceil(currentPage / (double)BLOCK_SIZE)) * BLOCK_SIZE;
		// 시작번호
		this.startPage = this.endPage - (BLOCK_SIZE - 1);
		// 예외 처리
		if( this.totalPages < this.endPage )
			this.endPage = this.totalPages;
		
		// 이전
		this.prev = this.startPage > 1;
		// 다음
		this.next = this.endPage < this.totalPages;
	}

}
