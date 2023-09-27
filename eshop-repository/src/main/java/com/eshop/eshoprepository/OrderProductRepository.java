package com.eshop.eshoprepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.logistics.OrderProduct;

/**
 * Repository for Order Products
 */

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

}
