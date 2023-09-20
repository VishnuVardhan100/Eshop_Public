package com.eshop.eshopmodel;

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
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="UserAddress")
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
	
	@ManyToOne
	@JoinTable(
			name="USER_USERADDRESS",
			joinColumns= {@JoinColumn(name="ProductID",referencedColumnName="ProductID") },
			inverseJoinColumns= { @JoinColumn(name="UserID", referencedColumnName="UserID") }
			)
	private User user;
	
	public UserAddress() {
		
	}

	public UserAddress(int id,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[0-9_-:\\s]{1,8}$", message = "Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters") String houseNo,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9\\s]{5,20}$", message = "Alphabets, spaces and digits allowed. Between 5-20 characters") String street,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z\\s]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String city,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String state,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[0-9]{6}$", message = "Only Digits and must be exact 6 characters") String pincode,
			User user) {
		super();
		this.id = id;
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserAddress [id=" + id + ", houseNo=" + houseNo + ", street=" + street + ", city=" + city + ", state="
				+ state + ", pincode=" + pincode + ", user=" + user + "]";
	}

}
