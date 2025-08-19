package com.example.board.upload;

import java.time.LocalDateTime;

import com.example.board.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_files")
@NoArgsConstructor // 기본 생성자
@Getter
public class ImageFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String originalFilename; // 원본파일명

	@Column(nullable = false, unique = true)
	private String storedFilename; //실제로 저장된 파일명( uuid )
	
	@Column(nullable = false)
	private String contentType;
	
	@Column(nullable = false)
	private Long size; // 파일크기
	
	@Column(length = 300)
	private String url; // 이미지 접근 주소
	
	@Column(length = 300)
	private String path; // 이미지 저장 경로
	
	@Column(nullable = false)
	private LocalDateTime createdAt; // 이미지저장 시간
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User user; // 엔티티 관계도 설정

	public ImageFile(String originalFilename, String storedFilename, String contentType, Long size, String url,
			String path, User user) {
		this.originalFilename = originalFilename;
		this.storedFilename = storedFilename;
		this.contentType = contentType;
		this.size = size;
		this.url = url;
		this.path = path;
		this.createdAt = LocalDateTime.now();
		this.user = user;
	}
	
	
	
}
