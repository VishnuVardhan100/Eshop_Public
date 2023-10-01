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
			+ "cu.Customer_ID = :customerID and lo.order_ID = :orderID", nativeQuery = true)
	public Order findOrderByCustomerID(@Param("customerID") long customerID, @Param("orderID") long orderID);
	
	@Query(value = "select * from logistics_order lo inner join customer_logistics_order culo inner join customer cu where"
			+ "cu.Customer_ID = :customerID", nativeQuery = true)
	public List<Order> findAllOrdersByCustomerID(@Param("customerID") long customerID);

}
