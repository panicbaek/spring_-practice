package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 응답해주는 클래스
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
	
	private int status; // 상태코드
	private T data; // 응답 데이터
	
}
