package com.eshop.eshoprepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.customer.Customer;

/**
 * Repository class for handling Customer and CustomerAddress
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	/**
	 * Get customers by first name
	 * @param firstname
	 * @return list of matching customers
	 */
	@Query(value = "select * from Customer c where c.Customer_First_Name like :firstname", nativeQuery = true)
	public List<Customer> retreiveCustomersByFirstName(@Param("firstname") String firstname);

	/**
	 * Get customers by last name
	 * @param lastname
	 * @return list of matching customers
	 */
	@Query(value = "select * from Customer c where c.Customer_Last_Name like :lastname", nativeQuery = true)
	public List<Customer> retreiveCustomersByLastName(@Param("lastname") String lastname);

	/**
	 * Get customers by first name
	 * @param email
	 * @return list of matching customers
	 */
	@Query(value = "select * from Customer c where c.Customer_Email like :email", nativeQuery = true)
	public List<Customer> retreiveCustomersByEmail(@Param("email") String email);
	
}
