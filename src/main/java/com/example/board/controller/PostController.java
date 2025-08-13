package com.example.board.controller;
import com.example.board.repository.PostRepository;
import com.example.board.service.PostService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.domain.Post;
import com.example.board.domain.ResponseDTO;
import com.example.board.domain.User;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    private PostService postseService;

    PostController(PostRepository postRepository, PostService postService) {
        this.postService = postService;
    }
	
	@GetMapping("/post/insert")
	public String insert() {
		return "post/post";
	}
	
	@PostMapping("/post")
	@ResponseBody
	public ResponseDTO<?> insertPost(@RequestBody Post post, HttpSession session) {
		User writer = (User)session.getAttribute("principal"); // 세션정보는 객체이기 때문에 (User)로 형변환을 해야 변수에 담을 수 있음
		postService.insertPost(post, writer);
		
		return new ResponseDTO<>(HttpStatus.OK.value(),"새 게시글 등록완료");
	}
	
	@GetMapping("/")
	public String index(Model model) {
		
		List<Post> list = postService.getPostList();
		model.addAttribute("postList", list);
		
		return "index";
	}
	
}
