package com.example.controller;



import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OtpVerify;
import com.example.dto.ProfilePic;
import com.example.dto.UserLoginDto;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.exception.UserException;
import com.example.exception.UserRegistrationException;
import com.example.service.EmailService;
import com.example.service.UserServiceImple;

@RestController
@CrossOrigin

public class UserController {
	
	@Autowired
	private UserServiceImple userServ;
	
	@Autowired
	private EmailService emailservice;
	
	Map<String,Integer> userOtpSession = new HashMap<String, Integer>();
	
	@PostMapping("/register") 
	public boolean RegisterUser(@RequestBody User u) {
		
		Role r = new Role(1,"User");
		u.setRole(r);
		
		try {
			userServ.addUser(u);
				
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
	

}
