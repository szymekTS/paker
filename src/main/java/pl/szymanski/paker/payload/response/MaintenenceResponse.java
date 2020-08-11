package pl.szymanski.paker.payload.response;

import org.springframework.format.annotation.DateTimeFormat;
import pl.szymanski.paker.models.enums.ERepair;

import java.util.Date;

public class MaintenenceResponse {
    private String id;
    private String carId;
    private String description;
    private ERepair status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date doneTime;

    public MaintenenceResponse() {
    }

    public MaintenenceResponse(String id, String carId, String description, ERepair status, Date startTime, Date doneTime) {
        this.id = id;
        this.carId = carId;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.doneTime = doneTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ERepair getStatus() {
        return status;
    }

    public void setStatus(ERepair status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }
}
