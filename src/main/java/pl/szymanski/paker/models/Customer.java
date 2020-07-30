package pl.szymanski.paker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collation = "customers")
public class Customer {
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 20)
	private String name;
	

	@NotBlank
	@Size(max = 20)
	private String surname;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	public Customer() {
		super();
	}

	public Customer(String name, String surname,String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
