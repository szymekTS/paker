package pl.szymanski.paker.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import pl.szymanski.paker.models.enums.ERepair;

@Document(collection = "maintenance")
public class Maintenance {
    @Id
    private String id;

    @DBRef
    private Car car;

    private String problemDescription;

    private ERepair status;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date startTime;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date doneTime;

    public Maintenance() {
    }

    public Maintenance(Car car, String problemDescription) {
        this.car = car;
        this.problemDescription = problemDescription;
        this.status = ERepair.REPORTED;
        this.startTime = new Date();
        this.doneTime = this.startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public ERepair getStatus() {
        return status;
    }

    public void setStatus(ERepair status) {
        this.status = status;
        if (this.status == ERepair.DONE)
            this.doneTime = new Date();
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
