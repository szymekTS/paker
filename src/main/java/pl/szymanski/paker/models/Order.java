package pl.szymanski.paker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import pl.szymanski.paker.models.enums.EStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    @DBRef
    private Car car;
    @DBRef
    private Cargo cargo;
    @DBRef
    private Customer customer;
    @DBRef
    private User driver;
    @DBRef
    private List<Status> statusList = new ArrayList<>();

    private EStatus lastStatus;
    @DBRef
    private City localization;
    @DBRef
    private City origin;
    @DBRef
    private City destiny;
    @DBRef
    private Route route;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endTime;

    public Order() {
        startTime= new Date();
        endTime= new Date();
    }

    public Order(String id, Car car, Cargo cargo, Customer customer, List<Status> statusList, City origin, City destiny, Route route) {
        this();
        this.id = id;
        this.car = car;
        this.cargo = cargo;
        this.customer = customer;
        this.statusList = statusList;
        this.origin = origin;
        this.destiny = destiny;
        this.route = route;
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public EStatus getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(EStatus lastStatus) {
        this.lastStatus = lastStatus;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestiny() {
        return destiny;
    }

    public void setDestiny(City destiny) {
        this.destiny = destiny;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public City getLocalization() {
        return localization;
    }

    public void setLocalization(City localization) {
        this.localization = localization;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
