package com.eshop.eshopmodel;

import java.sql.Date;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	private int Id;

	@NotBlank(message="First Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]$", message="For Last name, regular alphabet and spaces are allowed. Max 20 characters")
	@Column(name="FirstName")
	private String firstName;

	@NotBlank(message="Last Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]$", message="For Last name, regular alphabet and spaces are allowed. Max 20 characters")
	@Column(name="LastName")
	private String lastName;

	@Email(message="")
	@Column(name="Email")
	private String email;

	@Column(name="CreatedDate")	
	private Date createdDate;

	public User() {
		
	}

	public User(int id,
			@NotBlank(message = "First Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]$", message = "For Fisrt name ,regular alphabet and spaces are allowed. Max 20 characters") String firstName,
			@NotBlank(message = "Last Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]$", message = "For Last name, regular alphabet and spaces are allowed. Max 20 characters") String lastName,
			@Email(message = "") String email, Date createdDate) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.createdDate = createdDate;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", createdDate=" + createdDate + "]";
	}

}
