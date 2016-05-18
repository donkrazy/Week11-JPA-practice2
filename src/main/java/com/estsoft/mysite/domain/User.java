package com.estsoft.mysite.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table( name = "user" )
public class User {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY  )
	private Long no;
	
	@NotEmpty
	@Column( length = 20, nullable = false )
	private String name;
	
	@NotEmpty
	@Email
	@Column( length = 200, nullable = false )
	private String email;
	
	@Column( name = "passwd", length = 32, nullable = false )
	private String password;

	@Enumerated ( EnumType.STRING )
	@Column( nullable = false, columnDefinition = "enum('Female', 'Male')" )
	private Gender gender;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + "]";
	}
}