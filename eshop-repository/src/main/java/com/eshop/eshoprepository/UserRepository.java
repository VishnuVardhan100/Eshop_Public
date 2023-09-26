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

	/**
	 * Get users by first name
	 * @param firstname
	 * @return list of matching users
	 */
	@Query(value = "select * from Consumer_User u where u.First_Name like :firstname", nativeQuery = true)
	public List<User> retreiveUsersByFirstName(@Param("firstname") String firstname);

	/**
	 * Get users by last name
	 * @param lastname
	 * @return list of matching users
	 */
	@Query(value = "select * from Consumer_User u where u.Last_Name like :lastname", nativeQuery = true)
	public List<User> retreiveUsersByLastName(@Param("lastname") String lastname);

	/**
	 * Get users by first name
	 * @param email
	 * @return list of matching users
	 */
	@Query(value = "select * from Consumer_User u where u.Email like :email", nativeQuery = true)
	public List<User> retreiveUsersByEmail(@Param("email") String email);
	
}
