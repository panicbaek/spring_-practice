package com.example.board.controller;
import com.example.board.repository.PostRepository;
import com.example.board.service.PostService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.board.BoardApplication;
import com.example.board.domain.PageDTO;
import com.example.board.domain.Post;
import com.example.board.domain.ResponseDTO;
import com.example.board.domain.User;

@Controller
public class PostController {

    private final BoardApplication boardApplication;

    private final PostService postService;

    @Autowired
    private PostService postseService;

    PostController(PostRepository postRepository, PostService postService, BoardApplication boardApplication) {
        this.postService = postService;
        this.boardApplication = boardApplication;
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
	
	@GetMapping("/")					// pageabledefault 중요 sort는 정렬 기준을정해줌, directin 은 정렬 순서 (기본은 오름차순)
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Direction.DESC) Pageable pageable) {
		
		Page<Post> list = postService.getPostList(pageable); // 페이지와 레코드를 담고있는 객체
		
		List<Post> l = list.getContent(); // 데이터만 가지고 있음
		
		model.addAttribute("pageDTO", new PageDTO(list));
		model.addAttribute("postList", list);
		// Page 객체가 가지고 있는 정보들
//		System.out.println("전체 페이지 수 : " + list.getTotalPages());
//		System.out.println("전체 레코드 수 : " + list.getTotalElements());
//		System.out.println("전체 페이지 번호 : " + list.getNumber());
//		System.out.println("페이지당 표시할 개수 : " + list.getSize());
//		System.out.println("페이지에 데이터가 있냐 없냐 : " + list.hasContent());
//		System.out.println("이전 페이지가 있냐 없냐 : " + list.hasPrevious());
//		System.out.println("다음 페이지가 있냐 없냐 : " + list.hasNext());
//		System.out.println("첫번째 페이지냐 ? : " + list.isFirst());
//		System.out.println("마지막 페이지냐 ? : " + list.isLast());
		
		return "index";
	}
	
	@GetMapping("/post/{id}")
	public String post(@PathVariable int id, Model model) {
		
		Post post = postService.getPost(id);
		
		model.addAttribute("post", post);
		
		return "/post/getPost";
	}
	
	@GetMapping("/post/update/{id}")
	public String update(@PathVariable int id, Model model) {
		
		Post post = postService.getPost(id);
		
		model.addAttribute("post", post);
		
		return "post/updatePost";
	}
	
	@PutMapping("/post")
	@ResponseBody
	public ResponseDTO<?> update(@RequestBody Post post) {
		
		postService.updatePost(post); // postService에서 updatePost 데이터 가져옴
		
		return new ResponseDTO<>(HttpStatus.OK.value(), post.getId() + "번 게시글 수정완료");
	}
	
	@Autowired
	private PostRepository postRepository;
	@ResponseBody
	@DeleteMapping("/post/{id}")
	public ResponseDTO<?> delete(@PathVariable int id) {
		
		postRepository.deleteById(id);
		
		return new ResponseDTO<>(HttpStatus.OK.value(), id + "번 게시글 삭제 완료");
	}
	
}
