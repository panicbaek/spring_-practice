package com.example.board.upload;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileDTO {

	private Long id;
	private String originalFilename;
	private String storedFilename;
	private String contentType;
	private Long size;
	private String url;
	private String path;
	private LocalDateTime createdAt;
	private String user;
	
	// ImageFile객체를 받아서 ImageFileDTO객체를 생성해주는 메서드
	public static ImageFileDTO form(ImageFile e) {
		
		return new ImageFileDTO(
					e.getId(),
					e.getOriginalFilename(),
					e.getStoredFilename(),
					e.getContentType(),
					e.getSize(),
					e.getUrl(),
					e.getPath(),
					e.getCreatedAt(),
					e.getUser().getUsername()
				);
	}
	
	
}
