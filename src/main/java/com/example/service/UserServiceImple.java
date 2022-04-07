package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.CartProductsDto;
import com.example.dto.UserLoginDto;
import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.entity.User;
import com.example.entity.UserProducts;
import com.example.exception.UserAlreadyPresent;
import com.example.exception.UserException;
import com.example.exception.UserRegistrationException;
import com.example.repository.GenericCartRepo;
import com.example.repository.GenericProductReopository;
import com.example.repository.GenericUserProductRepo;
import com.example.repository.GenericUserRepository;


@Service
@Transactional
public class UserServiceImple implements UserService {
	
	@Autowired
	private GenericUserProductRepo genUserproductsRepo;
	
	@Autowired
	private GenericUserRepository genUserRepo;
	
	@Autowired
	private GenericCartRepo genCartRepo;
	
	@Autowired
	private GenericProductReopository genProductRepo;
	

	@Override
	public User addUser(User u) {
		
		try
		{
			u.setPassword(((Integer)u.getPassword().hashCode()).toString());
			
			return genUserRepo.save(u);
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
			System.out.println(id);
			e.printStackTrace();
			throw new UserException("User with id not found");
		}
		
	}

	@Override
	public UserProducts addtoCartProduct(CartProductsDto p) {
		UserProducts cartProduct;
		try {
			
			
			User u = getUserById(p.getUserid());
			Cart c = genCartRepo.findByUser(u);
			
			cartProduct  = new UserProducts();
			
			cartProduct.setCart(c);
			Product product = genProductRepo.findById(p.getProductid()).get();
			UserProducts up = genUserproductsRepo.findByProduct(product);
			System.out.println(product);
			System.out.println(up);
			if(up==null)
				cartProduct.setProduct(product);
			else {
				throw new UserAlreadyPresent();
			}
	
			cartProduct.setQuantity(p.getQuantity());
			
			cartProduct.setVisiblity("Pending");
			
		return	genUserproductsRepo.save(cartProduct);
			
			
		}
		catch(UserAlreadyPresent e) {
			e.printStackTrace();
			throw new UserAlreadyPresent("User is already present");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new UserException("Cart");
		}
		
	}

	@Override
	public List<Product> getAllCartProducts(int cartId) {
		
		try {
			List<Product> product = new ArrayList<Product>();
			List<UserProducts> up = genUserproductsRepo.findAllProductsWhereVisiblityIsPendingAndCartIdIsPresent(genCartRepo.findById(cartId).get());
			for (UserProducts x : up) {
				
				product.add(x.getProduct());
			}
			up.get(0).getProduct();
			System.out.println(up);
			return product;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
