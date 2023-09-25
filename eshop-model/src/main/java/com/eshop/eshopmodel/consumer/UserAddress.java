package com.eshop.eshopmodel.consumer;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.CascadeType;
/**
 * Aggregation Entity class User Address for Base Entity User class. Used to store address
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class for User Address. Aggregation class.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name="ConsumerUserAddress")
@Validated
public class UserAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="AddressID")
	private int id;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[0-9_-:\\s]{1,8}$", message="Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters")
	@Column(name="houseNo")
	private String houseNo;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9\\s]{5,20}$", message="Alphabets, spaces and digits allowed. Between 5-20 characters")
	@Column(name="Street")
	private String street;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z\\s]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	@Column(name="City")
	private String city;

	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9\\s]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	@Column(name="State")
	private String state;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[0-9]{6}$", message="Only Digits and must be exact 6 characters")
	@Column(name="Pincode")
	private String pincode;
	
	@NotNull(message="User Address has to be for a respective user")
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="UserID")
	private User user;
		
	/**
	 * Parameterized constructor
	 * @param id
	 * @param houseNo
	 * @param street
	 * @param city
	 * @param state
	 * @param pincode
	 * @param user
	 */
	public UserAddress(int id,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[0-9_-:\\s]{1,8}$", message = "Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters") String houseNo,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9\\s]{5,20}$", message = "Alphabets, spaces and digits allowed. Between 5-20 characters") String street,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z\\s]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String city,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String state,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[0-9]{6}$", message = "Only Digits and must be exact 6 characters") String pincode,
			@NotNull(message="User Address has to be for a respective user") User user) {
		super();
		this.id = id;
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.user = user;
	}

	/**
	 * @return user address id
	 */
	public int getId() {
		return id;
	}

	/**
	 * set user address id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return user address house number (can be flat number too)
	 */
	public String getHouseNo() {
		return houseNo;
	}

	/**
	 * set user address house number (can be flat number too)
	 * @param houseNo
	 */
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	/**
	 * @return user address street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * set user address street
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return user address city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * set user address city
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return user address state
	 */
	public String getState() {
		return state;
	}

	/**
	 * set user address state
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return user address pin code
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * set user address pin code
	 * @param pincode
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return respective user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * set respective user
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * return User Address with ID, house number, street, city, state , pin code and respective user
	 */
	@Override
	public String toString() {
		return "UserAddress [id=" + id + ", houseNo=" + houseNo + ", street=" + street + ", city=" + city + ", state="
				+ state + ", pincode=" + pincode + ", user=" + user + "]";
	}

}
