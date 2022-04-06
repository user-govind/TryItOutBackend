package com.example.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderDto;
import com.example.dto.PaymentDto;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
@CrossOrigin
public class OrdersController {
	
	@PostMapping("/create-order")
	public OrderDto createOrder(@RequestBody PaymentDto order)
	{
		//System.out.println(order.getId()+"  "+ order.getAmount());
		Order razorOrder;
		OrderDto orderInfo = new OrderDto();
		try {
			RazorpayClient client = new RazorpayClient("rzp_test_0ANxMyDNRQqqnh", "284dRuK13Yyy2UeWKafenB6u");
			
			JSONObject options = new JSONObject();
			options.put("amount", order.getAmount()*100);
			options.put("currency", "INR");
			options.put("receipt", "txn_123456");
			razorOrder = client.Orders.create(options);
			
			orderInfo.setId(order.getId());
			orderInfo.setAmount(razorOrder.get("amount"));
			orderInfo.setOrderId(razorOrder.get("id"));
			orderInfo.setStatus(razorOrder.get("status"));
			orderInfo.setAttempts(razorOrder.get("attempts"));
			
			return orderInfo;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			orderInfo.setId(order.getId());
			orderInfo.setAmount(order.getAmount());
			orderInfo.setStatus("failed");
			return orderInfo;
		}
		
	}
}
