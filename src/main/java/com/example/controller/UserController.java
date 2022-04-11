package com.example.controller;



import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CartProductsRequestDto;
import com.example.dto.CartProductsResponseDto;
import com.example.dto.ClearCartRequestDto;
import com.example.dto.OtpVerify;
import com.example.dto.ProfilePic;
import com.example.dto.UserAddressRequestDto;
import com.example.dto.UserLoginDto;

import com.example.dto.UserProductDeleteRequestDto;

import com.example.dto.UserProductUpdateRequestDto;
import com.example.entity.Cart;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.exception.UserAlreadyPresent;
import com.example.exception.UserException;
import com.example.exception.UserRegistrationException;
import com.example.service.CartService;
import com.example.service.EmailService;
import com.example.service.UserServiceImple;

@RestController
@CrossOrigin

public class UserController {
	
	@Autowired
	private UserServiceImple userServ;
	
	@Autowired
	private EmailService emailservice;
	
	@Autowired
	private CartService cartService;
	

	
	
	Map<String,Integer> userOtpSession = new HashMap<String, Integer>();
	
	@PostMapping("/register") 
	public boolean RegisterUser(@RequestBody User u) {
		
		Role r = new Role(1,"User");
		u.setRole(r);
		
		try {
	
			u.setStatus("Active");

			User newUser = userServ.addUser(u);
			
			Cart c = new Cart();
			
			c.setUser(newUser);
			
			cartService.addUserCart(c);
			
				
		} catch (UserRegistrationException e) {
		
			return false;
		}
		
		return true;
	}
	
	@PostMapping("/login")
	public UserLoginDto CheckUser(@RequestBody UserLoginDto uld){
	
		UserLoginDto u = null;
		
		try {
			u = userServ.authenticateUser(uld);
			u.setStatus(true);
			return u;
		}
		catch(UserException e){
			u = userServ.authenticateUser(uld);
			u.setStatus(false);
			return u;
		}
	}
	
	@PostMapping("/upload-profilepic")
	public boolean uploadProfilePic( ProfilePic propic) {
		System.out.println(propic.getId()+"  "+propic.getProfilePic());
		String fileName = propic.getId() + "-"+propic.getProfilePic().getOriginalFilename();
		
		try {
			FileCopyUtils.copy(propic.getProfilePic().getInputStream(), new FileOutputStream("C:/Users/ASUS/Desktop/C-DAC KH/Final project/Front-end/try-it-out.com/src/User-ProfilePics/" + fileName));
		} catch (IOException e) {
			return false;
		}
		
		User u = userServ.getUserById(propic.getId());
		
		u.setImage(fileName);
		
		if(RegisterUser(u))
		return true;
		
		return false;
	}
	
	@GetMapping("/send-OTP/{email}")
	public boolean sendOtp(@PathVariable String email) {
		
		try {
		 	int otp = emailservice.sendEmailForOTP(email);
		 	
		 	
		 	userOtpSession.put(email, otp);
		 	System.out.println(userOtpSession.get(email));
		 	System.out.println(otp);
		 	
		 	
		}catch(Exception e) {
			e.printStackTrace();
			throw new UserException("Otp sending error");
		}
		
		return true;
	}
	
	@PostMapping("/verify-OTP")
	public boolean verifyOtp(@RequestBody OtpVerify otpBody) {
		System.out.println(otpBody.getEmail());
		 System.out.println(userOtpSession.get(otpBody.getEmail()));
		try {
			   if(userOtpSession.get(otpBody.getEmail()).equals(otpBody.getOtp()))
			   {
				 
				  return true;
				  
			   }
		}catch(Exception e) {
			e.printStackTrace();
			throw new UserException("Otp veryfing error");
		}
		
		return false;
	}
	
	@PostMapping("/add-product-cart")
	public boolean addtoCart(@RequestBody CartProductsRequestDto cartProducts ) {
		
		try {
			userServ.addtoCartProduct(cartProducts);
		}
		catch(UserAlreadyPresent e) {
			e.printStackTrace();
			throw new UserAlreadyPresent("User is already present");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new UserException("cart not available");
		}
		return true;
	}
	
	
	@PostMapping("/get-all-cartProducts/{cartId}")
	public List<CartProductsResponseDto> getCartProducts(@PathVariable int cartId ) {
		
		try
		{
			
			return userServ.getAllCartProducts(cartId);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new UserException("product are not available in cart");
		}
	}

	@PutMapping("/plus-UserProduct") 
	public Boolean plusproduct(@RequestBody UserProductUpdateRequestDto productDto) {
		
		try {
			return userServ.updateUserProductQuantityByadd1(productDto);
		}
		catch(UserException e) {
			return false;
		}
	}
	
	@PutMapping("/minus-UserProduct")
	public Boolean minusproduct(@RequestBody UserProductUpdateRequestDto productDto) {
		
		try {
			return userServ.updateUserProductQuantityBySub1(productDto);
		}
		catch(UserException e) {
			return false;
		}
	}
	@PutMapping("/delete-UserProduct")
	public Boolean deleteuserProduct(@RequestBody UserProductDeleteRequestDto productDto) {
		
		try {
			return userServ.deleteProductFromTheCart(productDto);
		}
		catch(UserException e) {
			return false;
		}
	}
  @GetMapping("/get-userCart/{uid}")
	public int getUserCartId(@PathVariable int uid) {
		
		try {
			return cartService.getCartId(uid);
		}
		catch(Exception e) {
			throw e;
		}
	}
  	@GetMapping("/update-User-Cart-checkout/{cid}")
	public boolean updateCartAfterCheckout(@PathVariable int cid) {
		
		try {
			userServ.updateUserCartProducts(cid);
			return true;
		}catch(Exception e) {
			throw e;
		}
	}	
  
  @PostMapping("/add-User-Address")
	public boolean addUserAddress(@RequestBody UserAddressRequestDto userAdd) {
		
		try {
			return userServ.addAddress(userAdd);
		}
		catch(Exception e) {
			throw e;
		}
	}
  
  
	@PutMapping("/clear-cart")
	public Boolean clearUserCart(@RequestBody ClearCartRequestDto productDto) {
		
		try {
			return userServ.clearCart(productDto);

    }	catch(Exception e) {
			throw e;
		}
  }
    
}