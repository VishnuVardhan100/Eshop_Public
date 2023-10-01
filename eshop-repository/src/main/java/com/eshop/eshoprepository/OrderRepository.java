package com.eshop.eshoprepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.logistics.Order;

/**
 * Repository to handle Orders
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query(value = "select lo from logistics_order lo inner join customer_logistics_order culo inner join customer cu where"
			+ "cu.User_ID = :userID and lo.order_ID = :orderID", nativeQuery = true)
	public Order findOrderByCustomerID(@Param("userID") int userID, @Param("orderID") int orderID);
	
	@Query(value = "select * from logistics_order lo inner join customer_logistics_order culo inner join customer cu where"
			+ "cu.User_ID = :userID", nativeQuery = true)
	public List<Order> findAllOrdersByCustomerID(@Param("userID") int userID);

}
