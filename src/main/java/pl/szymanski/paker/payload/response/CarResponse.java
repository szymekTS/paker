package pl.szymanski.paker.payload.response;

public class CarResponse {
    private String id;
    private String brand;
    private String model;
    private String licensePlate;
    private String carType;
    private String localization;
    private boolean inRepair;
    private boolean isFree;

    public CarResponse() {
    }

    public CarResponse(String id, String brand, String model, String licensePlate, String carType, String localization, boolean inRepair, boolean isFree) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.carType = carType;
        this.localization = localization;
        this.inRepair = inRepair;
        this.isFree = isFree;
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

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public boolean isInRepair() {
        return inRepair;
    }

    public void setInRepair(boolean inRepair) {
        this.inRepair = inRepair;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
