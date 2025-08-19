package com.example.board.upload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.domain.User;


@Service
public class UploadService {

	// 업로드 요청시 허용할 파일의 mime목록
	private static final Set<String> ALLOWED_TYPES = Set.of(
				"image/jpeg", "image/png", "image/gif", "image/webp"
			);
	
	// 저장할 경로를 담는 파일
	private final Path root;
	private final ImageFileRepository imageFileRepository;
	
	UploadService(StorageProperties props, ImageFileRepository imageFileRepository) {
		this.root = Paths.get(props.getUploadDir()).toAbsolutePath().normalize();
		this.imageFileRepository = imageFileRepository;
	}
	
	// 파일이 저장할 mime타입에 맞는지 검사하는 메서드
	public void validate(MultipartFile file) {
		if(file == null || file.isEmpty()) // 파일을 보냈냐 안보냈냐 if문으로 검사
			throw new IllegalArgumentException("파일이 없습니다.");
		String contentType = file.getContentType();
		if(contentType == null || !ALLOWED_TYPES.contains(contentType))
			throw new IllegalArgumentException("지원하지 않는 형식입니다..");
	}
	
	public static String ext(String name) {
		String clean = StringUtils.getFilename(name);
		if(clean == null)
			return "";
		
		int i = clean.lastIndexOf(".");
		
		return (i >= 0 ) ? clean.substring(i + 1).toLowerCase() : "";
	}
	
	@Transactional
	public ImageFile save(MultipartFile file, User user) throws IOException {
		validate(file);
		
		// 파일명 저장 코드
		String original = file.getOriginalFilename(); // 원본파일명
		// 파일의 확장자만 리턴을 해줌
		// 만약 확장자가 존재하지 않으면 빈문자열이 리턴됨
		// 확장자만 추출한 이유는 파일명 대신 UUID와 확장자만 표시하려고
		// ex) ASDASD_QWWEWQEW_QWEW.jpg
		String ex = ext(original);
		String stored = UUID.randomUUID() + "." + ex;
		
		Path target = root.resolve(stored).normalize();
		
		try (InputStream in = file.getInputStream() ){
			Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
			// 여기까지가 서버에 이미지 저장하는 코드임
		}
		// 이 아래는 DB에 이미지 파일 정보를 insert하는 코드
		String url = "/images/" + stored; // 이미지 요청 url
		String path = target.toString(); // 이미지가 저장된 경로 
		
		// 이미지 파일이라는 객체
		ImageFile imageFile = new ImageFile(
					original,
					stored,
					file.getContentType(),
					file.getSize(),
					url,
					path,
					user
				);
		
		return imageFileRepository.save(imageFile); // DB에 넣는 코드
		
	}
	
}
