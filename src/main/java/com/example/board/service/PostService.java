package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.repository.PostRepository;



@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public void insertPost(Post post, User writer) {
		post.setUser(writer);
		
		postRepository.save(post);
	}
	
	//인덱스 페이지로 이동시켜주는 작업
	@Transactional(readOnly = true)
	public List<Post> getPostList() {
		return postRepository.findAll();
	}
	
}
