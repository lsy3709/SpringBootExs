package com.pgm.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pgm.board.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	//@Modifying
	//@Query(value="insert into reply(writer,content, regDate, board_id, user_id) "
	//		+ "values(?1, ?2, now(), ?3, ?4)", nativeQuery=true)
	//public void replyInsert(String writer, String content, Long board_id, Long user_id);
	
	
	//Board.java  에서 user 가 @ManyToOne(fetch = FetchType.Eager)
	//@Query("select r  from reply r where board_id = ?1")
	
	//@ManyToOne(fetch = FetchType.LAZy)
	//LAZY 를 사용하면 
	@Query("select r from Reply r join fetch r.board where board_id=?1")
	public List<Reply> replyList(Long bno);

}
