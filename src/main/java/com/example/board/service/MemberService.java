package com.example.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.RoleType;
import com.example.board.domain.User;
import com.example.board.repository.UserRepository;

@Service
public class MemberService {
	
	@Autowired
	private UserRepository userRepository;
	
	// 컨트롤러에서 회원가입 정보를 받아와서
	// 권한을 부여하고 DB에 삽입해주는 메서드
	@Transactional
	public void insertUser(User user) {
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
	}
	
	// username을 이용해서 DB 조회
	public User getUser(String username) {
		// select 검색 결과가 있으면 해당 내용이 findUser에 담기고,
		// 없으면 new User() 생성된 빈객체가 findUser에 담김
		User findUser = userRepository.findByUsername(username).orElseGet(()-> {
			return new User();
		});
		return findUser;
	}
	
	public User updateUser(User user) {
		
		User findUser = userRepository.findById(user.getId()).get(); // 원본 꺼내오기
		
		findUser.setUsername(user.getUsername()); //원본데이터 교체
		findUser.setPassword(user.getPassword());
		findUser.setEmail(user.getEmail());
		
		userRepository.save(findUser); // 원본데이터 수정
		
		return findUser;
	}
	
	// 탈퇴 서비스 구간
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
	
	
}
