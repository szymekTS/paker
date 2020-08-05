package pl.szymanski.paker.payload.request;

public class CarRequest {
    private String brand;
    private String model;
    private String licensePlate;
    private String carType;

    public CarRequest() {
    }

    public CarRequest(String brand, String model, String licensePlate, String carType) {
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.carType = carType;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public boolean idValid(){
        return !this.brand.isEmpty() && !this.model.isEmpty() && !this.licensePlate.isEmpty() && !this.carType.isEmpty();
    }
}
