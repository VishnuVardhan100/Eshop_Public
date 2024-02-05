package com.eshop.eshopuserservice.model.customer;

import java.io.Serial;
import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.CascadeType;
/**
 * Aggregation Entity class customer Address for Base Entity customer class. Used to store address
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class for Customer Address. Aggregation class.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name="Customer_Address")
@Validated
public class CustomerAddress implements Serializable {

	/**
	 * Default Version
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Address_ID")
	private long addressID;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[-_:0-9 ]{1,8}$", message="Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters")
	@Column(name="house_No")
	private String houseNo;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9 ]{5,20}$", message="Alphabets, spaces and digits allowed. Between 5-20 characters")
	@Column(name="Street")
	private String street;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z ]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	@Column(name="City")
	private String city;

	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9 ]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	@Column(name="State")
	private String state;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[0-9]{6}$", message="Only Digits and must be exact 6 characters")
	@Column(name="Pincode")
	private String pincode;
	
	@NotNull(message="Customer Address has to be for a respective customer")
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(	name="Customer_And_Address",
				joinColumns = {@JoinColumn(name="Address_ID", referencedColumnName = "Address_ID")},
				inverseJoinColumns = {@JoinColumn(name="Customer_ID", referencedColumnName = "Customer_ID")})
	private Customer customer;

	/**
	 * Parameterized constructor
	 * @param AddressID addressID
	 * @param HouseNo houseNo
	 * @param Street street
	 * @param City city
	 * @param State state
	 * @param Pincode pincode
	 * @param Customer customer
	 */
	public CustomerAddress(long addressID,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[-_:0-9 ]{1,8}$", message = "Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters") String houseNo,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9 ]{5,20}$", message = "Alphabets, spaces and digits allowed. Between 5-20 characters") String street,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z ]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String city,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9 ]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String state,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[0-9]{6}$", message = "Only Digits and must be exact 6 characters") String pincode,
			@NotNull(message="Customer Address has to be for a respective customer") Customer customer) {
		super();
		this.addressID = addressID;
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.customer = customer;
	}

	/**
	 * @return customer address id
	 */
	public long getAddressID() {
		return addressID;
	}

	/**
	 * set customer address id
	 * @param AddressID addressID
	 */
	public void setAddressID(long addressID) {
		this.addressID = addressID;
	}

	/**
	 * @return customer address house number (can be flat number too)
	 */
	public String getHouseNo() {
		return houseNo;
	}

	/**
	 * set customer address house number (can be flat number too)
	 * @param HouseNo houseNo
	 */
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	/**
	 * @return customer address street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * set customer address street
	 * @param Street street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return customer address city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * set customer address city
	 * @param City city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return customer address state
	 */
	public String getState() {
		return state;
	}

	/**
	 * set customer address state
	 * @param State state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return customer address pin code
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * set customer address pin code
	 * @param Pincode pincode
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return respective customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * set respective customer
	 * @param Customer customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * return Customer Address with ID, house number, street, city, state , pin code and respective customer
	 */
	@Override
	public String toString() {
		return "CustomerAddress [ID = " + addressID + ", House No = " + houseNo + ", Street = " + street + ", City = " + city + ", State = "
				+ state + ", Pincode=" + pincode + ", Customer = " + customer + "]";
	}

}
