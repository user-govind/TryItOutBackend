package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Product;
import com.example.entity.UserProducts;

@Repository
public interface GenericUserProductRepo extends JpaRepository<UserProducts,Integer> {
	
	public UserProducts findByProduct(Product p);

}
