package com.example.board.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 매개변수 전체를 받아주는 생성자
@Entity // 테이블생성
@Table(name = "users") // 생성된 테이블명
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 일련번호 넣는 속성값
	private int id;
	
	@Column(nullable = false, length = 50, unique = true )
	private String username;
	
	@Column(length = 100)
	private String password;
	
	@Column(nullable = false, length = 100)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@CreationTimestamp
	private Timestamp createDate;
	

}
