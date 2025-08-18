package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.BoardApplication;
import com.example.board.domain.RoleType;
import com.example.board.domain.User;
import com.example.board.exception.BoardException;
import com.example.board.repository.UserRepository;


@RestController
public class UserController {

    private final BoardApplication boardApplication;
    
	
	// 첫번째 방법
	@Autowired
	private UserRepository userRepository;

    UserController(BoardApplication boardApplication) {
        this.boardApplication = boardApplication;
    }
	
	// 회원가입
	@PostMapping("/user")
	public String insertUser(@RequestBody User user) {
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		
		return user.getUsername() + "회원가입 성공";
	}
	
	// 회원조회 (회원의 id를 받아서 정보를 보여주는 기능)
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable int id) {
		
		// select * from users where id=?
		User findUser = userRepository.findById(id).orElseThrow(()-> {
			return new BoardException(id + "번 회원은 없습니다.");
		}); // orElseThrow 있으며 나오게하고 없으면 null처리함 

		
		return findUser;
	}
	
	// 회원정보 수정
	// 정보를 수정할 회원번호, 아이디, 비밀번호, 이메일을 포함해서 요청
	// 요청받은 정보를 이용해서 update작업이 처리되도록 구현
	@PutMapping("/user")
	public String updateUser(@RequestBody User user) { // user : 변경할 데이터
		
		// findUser : DB에 저장되어있던 원본 데이터
		User findUser = userRepository.findById(user.getId()).orElseThrow(()-> {
			return new BoardException(user.getId()+ "번 회원은 없음");
		});
		
		findUser.setUsername( user.getUsername() );
		findUser.setPassword( user.getPassword() );
		findUser.setEmail( user.getEmail() );
		System.out.println();
		userRepository.save(findUser);
		
		return "회원정보 수정 완료";
		
	}
	
	// 삭제
	// 회원번호를 클라이어트에게 받아서 그 번호인 레코드를 삭제
	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable int id) {
		// 기본키를 기준으로 검색문 처리해주는 메서드 : findById
		// 기본키를 기준으로 검색해서 삭제해주는 메서드 : deleteById
		System.out.println(id);
		userRepository.deleteById(id);
		
		
		
		return "삭제완료";
	}
	
	// 레코드 전체 조회 => findAll 메서드 ( select * from )
	@GetMapping("/user/list")
	public List<User> getUserList() {
		
		List<User> users = userRepository.findAll();
		
		return users;
	}
	

}

