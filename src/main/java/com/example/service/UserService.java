package com.example.service;

import java.util.List;

import com.example.dto.CartProductsDto;
import com.example.dto.UserLoginDto;
import com.example.entity.Product;
import com.example.entity.User;
import com.example.entity.UserProducts;

public interface UserService {

	public User addUser(User u);
	
	public UserLoginDto authenticateUser(UserLoginDto uld);
	
	public User getUserById(int id);
	
	public UserProducts addtoCartProduct(CartProductsDto c);
	
	public List<Product> getAllCartProducts(int cartid);
}
