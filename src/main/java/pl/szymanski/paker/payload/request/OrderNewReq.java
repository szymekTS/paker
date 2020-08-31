package pl.szymanski.paker.payload.request;

import pl.szymanski.paker.models.Item;

import java.util.List;

public class OrderNewReq {
    private String creator;
    private String car;
    private List<Item> cargo;
    private String customer;
    private String driver;
    private String origin;
    private String destiny;

    public OrderNewReq() {
    }

    public OrderNewReq(String creator, String car, List<Item> cargo, String customer, String driver, String origin, String destiny) {
        this.creator = creator;
        this.car = car;
        this.cargo = cargo;
        this.customer = customer;
        this.driver = driver;
        this.origin = origin;
        this.destiny = destiny;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public List<Item> getCargo() {
        return cargo;
    }

    public void setCargo(List<Item> cargo) {
        this.cargo = cargo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }
}
