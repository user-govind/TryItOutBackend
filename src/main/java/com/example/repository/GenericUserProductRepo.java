package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.entity.UserProducts;

@Repository
public interface GenericUserProductRepo extends JpaRepository<UserProducts,Integer> {
	
	public UserProducts findByProduct(Product p);
	

	@Query(value = "select * from user_products where visiblity='pending' and cart_id = :c",nativeQuery = true)
	public List<UserProducts> findAllProductsWhereVisiblityIsPendingAndCartIdIsPresent(@Param("c") Cart c);

}
