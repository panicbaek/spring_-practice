package com.example.board.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@NotNull(message = "username은 null이면 안됩니다.") // 값을 무조건 넣게 해주세요 
	@NotBlank(message = "username은 필수입력해야 합니다.") // 스페이스바 같은 빈공간은 빼주세요
	@Size(min = 1, max = 20, message = "username은 1~20 글자로 입력해야함")
	private String username;
	
	@Size(min = 3, max = 100, message = "password는 3~12 글자로 입력해야함")
	private String password;
	
	@NotNull(message = "이메일 쓰라고")
	@NotBlank(message = "이메일은 공백 안된다")
	@Email(message = "이메일이 아닌데?")
	private String email;
	
}
