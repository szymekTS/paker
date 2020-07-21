package pl.szymanski.paker.models;

import javax.persistence.*;

@Entity
@Table(name = "provinces")
public class Province {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private EProvince name;
	
	public Province() {
		super();
	}
	
	public Province(EProvince name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EProvince getName() {
		return name;
	}

	public void setName(EProvince name) {
		this.name = name;
	}
	
	
}
