package com.example.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		post.setCnt(0);
		
		postRepository.save(post);
	}
	
	//인덱스 페이지로 이동시켜주는 작업
	@Transactional(readOnly = true)
	public Page<Post> getPostList(Pageable pageable) {
		return postRepository.findAll(pageable);
	}
	
	public Post getPost(int id) {
		
		Optional<Post> op = postRepository.findById(id);
		
		return op.get();
	}
	
	@Transactional
	public void updatePost(Post post) {
		Post originPost = getPost(post.getId());
		
		originPost.setTitle(post.getTitle());
		originPost.setContent(post.getContent());
		
		postRepository.save(originPost);
	}
	
	
}
