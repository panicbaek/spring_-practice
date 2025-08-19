package com.example.board.upload;





import java.util.List;

import org.springframework.aot.generate.InMemoryGeneratedFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadViewController {

	@Autowired
	private ImageFileRepository imageFileRepository;
	
	@GetMapping("/upload")
	public String upload(Model model) {
		List<ImageFileDTO> list = imageFileRepository.findAll()
								.stream() // 반복 준비
								.map(ImageFileDTO::form) //반복
								.toList(); // 반복된거 리스트에 넣음
		
		model.addAttribute("images", list);
		
		return "upload/uploadList";
	}
	
}
