package com.pgm.board.service;

import com.pgm.board.model.User;

public interface UserService {
	
	public void register(User user);

	public User findByUsername(String username);

}
