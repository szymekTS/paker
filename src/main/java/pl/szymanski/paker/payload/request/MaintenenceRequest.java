package pl.szymanski.paker.payload.request;

public class MaintenenceRequest {
    private String carId;
    private String description;

    public MaintenenceRequest() {
    }

    public MaintenenceRequest(String carId, String description) {
        this.carId = carId;
        this.description = description;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        return !this.carId.isEmpty();
    }
}
