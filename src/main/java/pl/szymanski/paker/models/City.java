package pl.szymanski.paker.models;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
public class City {
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 40)
	private String name;
	
	@NotBlank
	@Size(max = 5)
	private String zipCode;
	
	@DBRef
	private Province province;


	private Map<String, Double> neighbours = new HashMap<String, Double> ();
	
	public City() {
	}
	
	public City(String name, String zipCode, Province province) {
		this.name = name;
		this.zipCode = zipCode;
		this.province = province;
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

	public Map<String,Double> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Map<String,Double> neighbours) {
		this.neighbours = neighbours;
	}
	public void addNeighbours(City city, Double distance){
		if(this.neighbours.containsKey(city.getId()))
			return;
		this.neighbours.put(city.getId(),distance);
		city.addNeighbours(this, distance);
	}

	@Override
	public String toString() {
		return "City" +
				"\n[" +
				"\n id : " + this.getId() +
				"\n name: " +  this.getName() +
				"\n neighbours: " + this.getNeighbours().size()+
				"\n   {" +
				"\n      "+ this.getNeighbours().keySet() + " : " +this.getNeighbours().values()+
				"\n   }" +
				"\n]";
	}
}
