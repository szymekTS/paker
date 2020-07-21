package pl.szymanski.paker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.*;

@Entity
@Table(	name = "cities")
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Size(max = 40)
	private String name;
	
	@NotBlank
	@Size(max = 5)
	private String zipCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "cities_provinces",
			joinColumns = @JoinColumn(name = "city_id"), 
			inverseJoinColumns = @JoinColumn(name = "province_id"))
	private Province province;
	
	public City() {
	}
	
	public City(String name, String zipCode, Province province) {
		this.name = name;
		this.zipCode = zipCode;
		this.province = province;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
	
	
}
