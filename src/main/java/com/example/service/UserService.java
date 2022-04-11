package com.example.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.dto.CartProductsRequestDto;
import com.example.dto.CartProductsResponseDto;
import com.example.dto.UserLoginDto;
import com.example.dto.UserProductDeleteRequestDto;
import com.example.dto.UserProductUpdateRequestDto;
import com.example.dto.CartProductsRequestDto;
import com.example.dto.CartProductsResponseDto;
import com.example.dto.UserLoginDto;
import com.example.entity.Product;
import com.example.entity.User;
import com.example.entity.UserProducts;

public interface UserService {

	public User addUser(User u);
	
	public UserLoginDto authenticateUser(UserLoginDto uld);
	
	public User getUserById(int id);
	
	public UserProducts addtoCartProduct(CartProductsRequestDto c);
	
	public List<CartProductsResponseDto> getAllCartProducts(int cartid);
	
	public boolean updateUserProductQuantityByadd1(UserProductUpdateRequestDto updto);
	
	public boolean updateUserProductQuantityBySub1(UserProductUpdateRequestDto updto);
	
	public boolean deleteProductFromTheCart(UserProductDeleteRequestDto delprod);
	
	public Boolean clearCart(int cartId);
}
