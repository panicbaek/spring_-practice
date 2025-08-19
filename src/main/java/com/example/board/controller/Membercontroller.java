package com.example.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.HttpSessionMutexListener;

import com.example.board.domain.ResponseDTO;
import com.example.board.domain.User;
import com.example.board.domain.UserDTO;
import com.example.board.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MemberController {
	
	// 의존성 주입
	@Autowired
	private MemberService memberService;
	@Autowired
	private ModelMapper modelMapper;
	
	// 요청받으면 회원가입 페이지로 넘어가게 만드는 클래스
	@GetMapping("/auth/join")
	public String join() {
		return "user/join";
	}
	
	// 프론트에서 데이터를 받아서 저장함
	// 첫번째 방법
	@PostMapping("/auth/join")
	@ResponseBody					// Valid는 유효성 검사 시켜라 하는 어노테이션임 BindingResult 는 유효성 검사를 담는 객체임
	public ResponseDTO<?> insertUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
		
//		// 유효성 검사에 부적합했을때 처리하는 코드
//		if(bindingResult.hasErrors()) {
//			Map<String, String> errorMap = new HashMap<>();
//			
//			for(FieldError error : bindingResult.getFieldErrors()) {
//				errorMap.put(error.getField(), error.getDefaultMessage());
//			}
//			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
//		}
		
		User user = modelMapper.map(userDTO, User.class);
		
		User findUser = memberService.getUser(user.getUsername());
		
		if(findUser.getUsername() == null ) {
			memberService.insertUser(user);
			
			// HttpStatus << 상태코드를 나타는 값 (번호를 보고 뭐가 문제인지 바로 파악가능)
			return new ResponseDTO<>( HttpStatus.OK.value(), user.getUsername() + "님 회원가입 성공!" );
		} else {
			return new ResponseDTO<>( HttpStatus.BAD_REQUEST.value(), user.getUsername() + "님은 이미 가입한 회원입니다.");
		}
		
	}
	
	// 두번째 방법
	@PostMapping("/auth/join2")
	public String insertUser2(User user) {
		memberService.insertUser(user);
		
		return "index";
	}
	@GetMapping("/auth/login")
	public String login() {
		return "user/login";
	}
	
	// 아이디, 비밀번호 data받아서 저장
	@PostMapping("/auth/login")
	@ResponseBody
	public ResponseDTO<?> login(@RequestBody User user, HttpSession session) {
		
		User findUser = memberService.getUser(user.getUsername());
		
		if(findUser.getUsername() == null ) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "잘못된 아이디 입니다.");
		} else {
			if(user.getPassword().equals(findUser.getPassword())) {
				// 로그인 성공
				session.setAttribute("principal", findUser);
				
				return new ResponseDTO<>(HttpStatus.OK.value(), findUser.getUsername() + "님 로그인");
			} else {
				return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "잘못된 비밀번호 입니다.");
			}
		}
	}
	
	@GetMapping("/auth/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // session 제거 함수
		
		return "redirect:/"; // Redirect : 클라이언트가 요청한 주소에서 다른 요청 주소로 넘김
	}
	
	// 회원정보 페이지
	@GetMapping("/auth/info")
	public String info(HttpSession session, Model model) {
		// 나중에 작업할 때 세션에 담긴 정보는 제한적이라고 가정 ( username만 있다고 가정 )
		User principal = (User)session.getAttribute("principal");
		
		User userInfo = memberService.getUser(principal.getUsername());
		
		model.addAttribute("info", userInfo);
		
		return "user/info";
	}
	
	// 회원정보 수정
	@PostMapping("/auth/info")
	@ResponseBody
	public ResponseDTO<?> info(@RequestBody User user, HttpSession session) {
		
		User findUser = memberService.updateUser(user);
		
		session.setAttribute("principal", findUser);
		
		return new ResponseDTO<>(HttpStatus.OK.value(), "회원정보 수정완료");
	}
	
	// 회원탈퇴 구간
	@ResponseBody
	@DeleteMapping("/auth/user")
	public ResponseDTO<?> delete(@RequestParam int id, HttpSession session) {
		
		memberService.deleteUser(id); 
		session.invalidate();
		
		return new ResponseDTO<>(HttpStatus.OK.value(), "회원탈퇴 완료");
	}
	
	
}
