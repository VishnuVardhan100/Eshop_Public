package com.eshop.eshoprepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.consumer.User;

/**
 * Repository class for handling User and UserAddress
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "select u from CustomerUser u where u.FirstName like =:firstname", nativeQuery = true)
	public List<User> retreiveUsersByFirstName(@Param("firstname") String firstname);

	@Query(value = "select u from CustomerUser u where u.LastName like =:lastname", nativeQuery = true)
	public List<User> retreiveUsersByLastName(@Param("lastname") String lastname);

	@Query(value = "select u from CustomerUser u where u.Email like =:email", nativeQuery = true)
	public List<User> retreiveUsersByEmail(@Param("email") String email);
	
}
