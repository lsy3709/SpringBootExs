package com.pgm.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgm.board.model.Board;
import com.pgm.board.model.Reply;
import com.pgm.board.model.User;
import com.pgm.board.service.BoardService;
import com.pgm.board.service.UserService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/replies")
public class ReplyController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	
	@PostMapping("new/{bno}")
	public ResponseEntity<String> replyInsert(@PathVariable Long bno,
			@RequestBody Reply reply){
		Board b=new Board();
		b.setId(bno);
		reply.setBoard(b);
		User user=userService.findByUsername(reply.getWriter());
		reply.setUser(user);
		boardService.insetReply(reply);
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	@GetMapping("getList/{bno}")
	public List<Reply> list(@PathVariable Long bno){
		List<Reply> rList=boardService.replyList(bno);
		//log.info("rList[0]"+rList.get(0));
		return rList;
	}
	@DeleteMapping("{id}")
	public Long delete(@PathVariable Long id) {
		boardService.replyDelete(id);
		return id;
	}
	
	

}
