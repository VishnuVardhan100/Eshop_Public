package com.eshop.eshoprepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.customer.CustomerAddress;

/**
 * Repository class for handling CustomerAddress
 */

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long>{

}
