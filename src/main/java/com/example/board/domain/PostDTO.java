package com.example.board.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
	
	@NotNull(message = "제목을 입력해 주세요")
	@Size(min = 1, max = 20, message = "1글자 이상 20글자 이내로 작성해주세요")
	@NotBlank(message = "공백은 안됩니다")
	private String title;
	
	@NotBlank(message = " 공백은 안됩니다")
	@NotNull(message = "내용을 입력해 주세요")
	private String content;
	
}
