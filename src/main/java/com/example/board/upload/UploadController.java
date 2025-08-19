package com.example.board.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.domain.ResponseDTO;
import com.example.board.domain.User;

@RestController
@RequestMapping("/api/images")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;

	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseDTO<?> upload(@RequestPart MultipartFile file, @SessionAttribute("principal") User user) throws Exception {
		
		ImageFile imageFile = uploadService.save(file, user);
		
		return new ResponseDTO<>(HttpStatus.OK.value(), ImageFileDTO.form(imageFile));
	}
	
}