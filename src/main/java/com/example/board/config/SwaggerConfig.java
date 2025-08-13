package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().components(new Components()).info( apiInfo() );
	}
	
	private Info apiInfo() {
		return new Info()
				.title("연습용 API 문서")
				.description("이거는 스웨거로 api문서를 만들어보는걸 연습해보는거입니다.")
				.version("0.0.1");
	}
	
	@Operation(summary = "GET 메서드 예제", description = "이것저것 설명 넣는곳")
	@GetMapping("/swagger")
	public String swager(
			@Parameter(name = "name", description = "이름", required = true) 
			@RequestParam String name,
			@Parameter(name = "age", description = "나이", required = true)
			@RequestParam String age) {
		
		
		return null;
	}
	
}
