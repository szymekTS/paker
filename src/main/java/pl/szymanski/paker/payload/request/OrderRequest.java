package pl.szymanski.paker.payload.request;

import pl.szymanski.paker.models.enums.EStatus;

import java.util.List;

public class OrderRequest {
    private String id;
    private String car;
    private String cargo;
    private String customer;
    private List<String> statusList;
    private EStatus lastStatus;
    private String origin;
    private String destiny;

    public OrderRequest() {
    }

    public OrderRequest(String id, String car, String cargo, String customer, List<String> statusList, EStatus lastStatus, String origin, String destiny) {
        this.id = id;
        this.car = car;
        this.cargo = cargo;
        this.customer = customer;
        this.statusList = statusList;
        this.lastStatus = lastStatus;
        this.origin = origin;
        this.destiny = destiny;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public EStatus getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(EStatus lastStatus) {
        this.lastStatus = lastStatus;
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
