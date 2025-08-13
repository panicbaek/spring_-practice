package com.example.board.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.board.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 로그인 페이지로 강제하는 함수
public class AuthInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		User principal = (User)session.getAttribute("principal");
		
		if(principal == null) {
			response.sendRedirect("/auth/login"); // 로그인페이지로 이동
		}
		
		return true; // 리턴타입이 boolean이기때문에 true로 바꿈
	}
	
	
	
}
