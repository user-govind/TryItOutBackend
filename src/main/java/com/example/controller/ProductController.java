package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.exception.UserException;
import com.example.service.ProductServiceImple;

@RestController
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductServiceImple productService;
	
	@PostMapping("/add-product")
	public boolean addproduct(@RequestBody Product p) {
		p.setStatus("Added");
		try {
			productService.addProduct(p);
			return true;
		}
		catch(UserException e) {
			return false;
		}	
	}
	
	@PostMapping("/all-products")
	public List<Product> featchAllProducts(){
		try {
		return productService.getAllProducts();
		}
		catch(UserException e) {
			return null;
		}
	}
	@PutMapping("/delete-product/{id}")
	public boolean deleteproduct(@PathVariable int id) {
		try {
		
			return productService.deleteProduct(id); 
		}
		catch(UserException e) {
			return false;
		}
	}
}
