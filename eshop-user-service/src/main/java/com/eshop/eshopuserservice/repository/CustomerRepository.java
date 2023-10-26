package com.eshop.eshopuserservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eshop.eshopuserservice.model.customer.Customer;

/**
 * Repository class for handling Customer and CustomerAddress
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	/**
	 * Return Email if exists
	 * @param customerEmail
	 * @return matching email
	 */
	@Query(value = "select cu.Customer_Email from Customer cu where cu.Customer_Email ilike :customerEmail", nativeQuery=true)
	public String emailExists(@Param("customerEmail") String customerEmail);
	
	/**
	 * Return Customer by checking email
	 * @param customerEmail
	 * @return customer
	 */
	@Query(value = "select * from Customer cu where cu.Customer_Email ilike :customerEmail", nativeQuery=true)
	public Customer loadCustomerByEmail(@Param("customerEmail") String customerEmail);

	/**
	 * Get customers by first name
	 * @param firstname
	 * @return list of matching customers
	 */
	@Query(value = "select * from Customer c where c.Customer_First_Name ilike :firstname", nativeQuery = true)
	public List<Customer> retrieveCustomersByFirstName(@Param("firstname") String firstname);

	/**
	 * Get customers by last name
	 * @param lastname
	 * @return list of matching customers
	 */
	@Query(value = "select * from Customer c where c.Customer_Last_Name ilike :lastname", nativeQuery = true)
	public List<Customer> retrieveCustomersByLastName(@Param("lastname") String lastname);
	
}
