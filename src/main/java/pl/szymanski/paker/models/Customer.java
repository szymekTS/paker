package pl.szymanski.paker.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "customers", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "email") 
		})
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
