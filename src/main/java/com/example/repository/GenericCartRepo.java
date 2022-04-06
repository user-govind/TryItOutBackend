package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Cart;
import com.example.entity.User;

public interface GenericCartRepo extends JpaRepository<Cart,Integer> {

	public Cart findByUser(User u);
}
