package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.domain.Reply;
import com.example.board.domain.ResponseDTO;
import com.example.board.domain.User;
import com.example.board.service.ReplyService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyController {

	@Autowired
    private final ReplyService replyService;
	
	
    @PostMapping("/reply/{postId}")
    @ResponseBody
	public ResponseDTO<?> insertReply(@PathVariable int postId, @RequestBody Reply reply, HttpSession sesstion) {
    	User principal = (User)sesstion.getAttribute("principal");
    	// 작성자 정보, 댓글번호, 댓글 내용까지 뽑아옴
    	replyService.insertReply(postId, reply, principal);
    	
    	return new ResponseDTO<>(HttpStatus.OK.value(), postId + "번 게시물에 댓글 등록 완료");
	}
    
    @DeleteMapping("/reply/{replyId}")
    @ResponseBody
    public ResponseDTO<?> delete(@PathVariable int replyId) {
    	
    	replyService.deleteReply(replyId);
    	
    	return new ResponseDTO<>(HttpStatus.OK.value(), replyId + "번 댓글 삭제 완료");
    }
	


}
