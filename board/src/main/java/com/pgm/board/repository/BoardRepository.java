package com.pgm.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pgm.board.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {


	Page<Board> findByTitleContaining(String word, Pageable pageable);

	 

}
