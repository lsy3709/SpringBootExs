package com.pgm.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgm.board.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);
}
