package com.example.board.advice;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.board.domain.ResponseDTO;

@Component
@Aspect // 관점추가 
public class ValidationCheckAdvice {

	// 포인트컷 표현식
	@Around("execution(* com.example..controller.*Controller.*(..))")
	public Object validationCheck(ProceedingJoinPoint jp) throws Throwable {
		
		Object[] args = jp.getArgs();
		
		for(Object arg : args) {
			// arg가 유효성검사 결과를 가지고 있는 객체냐? ( bindingResult냐? )
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					
					return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
				}
				
			}
		}
		return jp.proceed();
	}
	
}
