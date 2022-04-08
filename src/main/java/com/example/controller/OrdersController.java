package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderRequestDto;
import com.example.dto.OrderResponseDto;
import com.example.dto.PaymentRequestDto;
import com.example.exception.UserException;
import com.example.service.OrderServiceImple;

@RestController
@CrossOrigin
public class OrdersController {
	
	
	@Autowired
	private OrderServiceImple orderSevice;
	
	
	
	@PostMapping("/create-order")
	public OrderResponseDto createOrder(@RequestBody OrderRequestDto order)
	{
		try {
			return orderSevice.createOrder(order);
			
		}
		catch(Exception e) {
			throw new UserException("Order Failed!!!");
		}
	}
	
	
	@PostMapping("/order-payment")
	public boolean orderPayment(@RequestBody PaymentRequestDto paymentdto) {
		
		try {
			return orderSevice.orderPayment(paymentdto);
		}
		catch(Exception e) {
			throw new UserException("Payment Failed!!!");
		}
		
	}
}



