package com.pgm.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pgm.board.model.Board;
import com.pgm.board.model.Reply;
import com.pgm.board.model.User;

public interface BoardService {
	
	public void insert(Board board,User user);
	
	public List<Board> boardList();
	
	public Board findById(Long id);
	
	public void update(Board board);
	public void delete(Long id);

	public Long count();
	
	public void insetReply(Reply reply);
	
	public List<Reply> replyList(Long bno);
	
	public void replyDelete(Long id);

	public Page<Board> findAll(Pageable pageable);	
	
	//검색 
	public Page<Board> search(String word,Pageable pageable );
	

}
