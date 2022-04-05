package com.example.service;

import com.example.entity.Product;

public interface ProductService {

	public boolean addProduct(Product p);
	
	public boolean deleteProduct(int id);
}
