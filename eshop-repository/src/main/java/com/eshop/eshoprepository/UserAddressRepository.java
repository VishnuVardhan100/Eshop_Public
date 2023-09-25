package com.eshop.eshoprepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.consumer.UserAddress;

/**
 * Repository class for handling UserAddress
 */

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer>{

}
