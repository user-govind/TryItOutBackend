package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface GenericUserRepository extends JpaRepository<User,Integer > {
	
	public User findByEmailAndPassword(String email , String pass);
	
	
	
}
