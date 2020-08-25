package pl.szymanski.paker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cars")
public class Car {

	@Id
	private String id;
	private String brand;
	private String model;
	private String licensePlate;

	private float width;
	private float depth;
	private float height;

	@DBRef
	private CarType type;

	private boolean inRepair;

	public Car() {
	}

	public Car(String brand, String model, String licensePlate, CarType type) {
		this.brand = brand;
		this.model = model;
		this.licensePlate = licensePlate;
		setType(type);
		this.inRepair = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public CarType getType() {
		return type;
	}

	public void setType(CarType type) {
		this.type = type;
		this.width = type.getType().width;
		this.depth = type.getType().depth;
		this.height = type.getType().height;
	}

	public float getWidth() {
		return width;
	}

	public float getDepth() {
		return depth;
	}

	public float getHeight() {
		return height;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public boolean isInRepair() {
		return inRepair;
	}

	public void setInRepair(boolean inRepair) {
		this.inRepair = inRepair;
	}
}
