package com.example.dto;

import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

public class ProductAddDto {
	
	
	private String Name;

	private String Description;
	
	private int quantity;
	
	private double price;


	
	private String category;
	
	private String gender;
	
	private String colour;
	
	private String brand;
	
	private MultipartFile productImg;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public MultipartFile getProductImg() {
		return productImg;
	}

	public void setProductImg(MultipartFile productImg) {
		this.productImg = productImg;
	}
	
	
	
}