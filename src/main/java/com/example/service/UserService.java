package com.example.service;

import com.example.dto.UserLoginDto;
import com.example.entity.User;

public interface UserService {

	public User addUser(User u);
	
	public UserLoginDto authenticateUser(UserLoginDto uld);
	
	public User getUserById(int id);
}
