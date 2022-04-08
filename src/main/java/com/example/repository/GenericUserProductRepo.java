package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.entity.UserProducts;
import com.example.utility.UserDefinedUserProductsDto;

@Repository
public interface GenericUserProductRepo extends JpaRepository<UserProducts,Integer> {
	
	public UserProducts findByProductAndCartAndVisiblity(Product p,Cart c,String v);
	

	@Query(value = "select * from user_products where visiblity='pending' and cart_id = :c",nativeQuery = true)
	public List<UserProducts> findAllProductsWhereVisiblityIsPendingAndCartIdIsPresent(@Param("c") Cart c);
	
<<<<<<< HEAD
	@Query(value = "select id as Id , price as Price, payment_table.payment_date as PaymentDate, "
			+ "payment_table.amount as Amount, payment_table.payment_id as PaymentId, payment_table.provider as "
			+ "Provider,payment_table.status as Status , order_item.order_id as OrderId, user_table.user_id as UserId, "
			+ "product_table.product_id as ProductId, product_table.name as ProductName, product_table.product_img as ProductImg , "
			+ "brand as Brand, category as Category, user_products.quantity as Quantity, user_products.colour as Colour, "
			+ "user_products.size as Size, receipt as Receipt from user_products join cart_table join user_table join payment_table "
			+ "join order_item join product_table where user_products.cart_id = cart_table.cart_id and product_table.product_id = "
			+ "user_products.product_id and cart_table.user_user_id = user_table.user_id and cart_table.cart_id = order_item.cart_id "
			+ "and payment_table.order_id = order_item.order_id and user_products.cart_id = :cid and user_products.visiblity = 'Bought' group by user_products.product_id",nativeQuery = true)
	public List<UserDefinedUserProductsDto> getAllOrderDetails(@Param("cid") int id);
	
	@Modifying
	@Query(value = "update user_products set visiblity = 'Bought' where cart_id = :cid", nativeQuery = true)
	public void updateCartProductsVisiblity(@Param("cid") Cart c);
	
	

}
//
//select id as Id, price as Price from user_products join cart_table join user_table join product_table where user_products.cart_id = cart_table.cart_id and product_table.product_id = user_products.product_id and cart_table.user_user_id = user_table.user_id and user_products.cart_id = :cid and user_products.visiblity = 'Bought'" ,nativeQuery = true)
=======
	@Modifying
	@Query(value="update user_products set quantity = :q + 1 where cart_id = :cid",nativeQuery=true)
	//public UserProducts updateUserProductQuantityByplus1(@Param("q") int quantity,@Param("cid") int cartId);
	public void updateUserProductQuantityByplus1(@Param("q") int quantity,@Param("cid") int cartId);
}
	
>>>>>>> 369a6020d857435cfc34ed77c6cbe2e263caa262
