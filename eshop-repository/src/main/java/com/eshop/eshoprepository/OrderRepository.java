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
public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query(value = "select lo from logistics_order lo inner join consumer_user_logistics_order culo inner join consumer_user cu where"
			+ "cu.user_ID = :userID and lo.order_ID = :orderID", nativeQuery = true)
	public Order findOrderByUserID(@Param("userID") int userID, @Param("orderID") int orderID);
	
	@Query(value = "select * from logistics_order lo inner join consumer_user_logistics_order culo inner join consumer_user cu where"
			+ "cu.user_ID = :userID", nativeQuery = true)
	public List<Order> findAllOrdersByUserID(@Param("userID") int userID);

}
