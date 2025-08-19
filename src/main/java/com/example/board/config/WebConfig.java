package com.example.board.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.board.upload.StorageProperties;

// 스프링프로젝트 전체 환경설정
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	// 추가적인 정적데이터를 접근 하도록 설정 변경
	// uploads폴더에 있는 이미지들을 접근할 수 있게 해줘야 함
	// 엔드포인트 설정 "/images/~~~~" -- url 세팅을함 이렇게 요청하면 uploads폴더에 접근해서 가져가라 ~
	@Autowired
	private StorageProperties props;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file:" + props.getUploadDir() + "/");
		
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new AuthInterceptor())
				.addPathPatterns("/","/post/**");
		
	}

	
	
}
