package com.eshop.eshoprepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.logistics.Order;

/**
 * Repository to handle Orders
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
