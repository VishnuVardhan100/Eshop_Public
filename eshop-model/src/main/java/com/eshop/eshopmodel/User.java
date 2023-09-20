package com.eshop.eshopmodel;

import java.sql.Date;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="User")
@Validated
public class User {

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserID")
	private int id;

	@NotBlank(message="First Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	@Column(name="FirstName")
	private String firstName;

	@NotBlank(message="Last Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	@Column(name="LastName")
	private String lastName;

	@Email(message="")
	@Column(name="Email")
	private String email;

	@NotBlank(message="Cannot be blank")
	@Pattern(regexp="^[6-9]{1}[0-9]{9}$", message="Must start with 6,7,8 or 9 and be 10 digits long")
	@Column(name="MobileNumber")
	private long mobileNumber;
	
	@Column(name="CreatedDate")	
	private Date createdDate;

	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinTable(
			name="USER_USERADDRESS",
			joinColumns= { @JoinColumn(name="UserID", referencedColumnName="UserID") },
			inverseJoinColumns= {@JoinColumn(name="ProductID",referencedColumnName="ProductID") }
			)
	private UserAddress userAddress;
	
	public User() {
		
	}

	public User(int id,
			@NotBlank(message = "First Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "For Fisrt name ,regular alphabet and spaces are allowed. Between 3-20 characters") String firstName,
			@NotBlank(message = "Last Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String lastName,
			@Email(message = "") String email,
			@NotBlank(message = "Cannot be blank") @Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Must start with 6,7,8 or 9 and be 10 digits long") long mobileNumber,
			Date createdDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.createdDate = createdDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}
	
	@Override
	public String toString() {
		return "User [ID=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", createdDate=" + createdDate + "]";
	}

}
