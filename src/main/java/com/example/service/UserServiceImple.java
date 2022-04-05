package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserLoginDto;
import com.example.entity.User;
import com.example.exception.UserException;
import com.example.exception.UserRegistrationException;
import com.example.repository.GenericUserRepository;

@Service
@Transactional
public class UserServiceImple implements UserService {
	
	@Autowired
	private GenericUserRepository genUserRepo;
	

	@Override
	public boolean addUser(User u) {
		
		try
		{
			u.setPassword(((Integer)u.getPassword().hashCode()).toString());
			genUserRepo.save(u);
			return true;
		}
		catch (Exception e){
			
			throw new UserRegistrationException("Already registered!");
			
		}
		
	}
	
	@Override
	public UserLoginDto authenticateUser(UserLoginDto uld) {
		User u= null;
		uld.setPassword(((Integer)uld.getPassword().hashCode()).toString());

		UserLoginDto userDetails = new UserLoginDto();
		try {
			if((u = genUserRepo.findByEmailAndPassword(uld.getEmail(),uld.getPassword()))!=null) {
				
				userDetails.setRoleId(u.getRole());
				userDetails.setUserId(u.getUserId());
				userDetails.setStatus(true);
				return userDetails;
			}
			else 
			{
				throw new UserException("User Not found!! Please try again.");
			}
		 
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new UserException("User Not found!! Please try again.");
		}
		
	}

	@Override
	public User getUserById(int id) {
		
		try {
			return genUserRepo.findById(id).get();
		}catch(Exception e)
		{
			throw new UserException("User with id not found");
		}
		
	}
}
