package com.example.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.Post;
import com.example.board.domain.Reply;
import com.example.board.domain.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	
	@Transactional // DB 데이터에서 꺼내오고 넣고 하는 작업을 해주기 위해서
	public void insertReply(int postId, Reply reply, User writer) {
		Post post = postRepository.findById(postId).get();
		reply.setPost(post);
		reply.setUser(writer);
		
		replyRepository.save(reply);
	}
	
	public void deleteReply(int replyId) {
		replyRepository.deleteById(replyId);
	}

}
