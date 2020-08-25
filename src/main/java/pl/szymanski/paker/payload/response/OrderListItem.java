package pl.szymanski.paker.payload.response;

public class OrderListItem {
    private String id;
    private String car;
    private String customer;
    private String lastStatus;
    private String origin;
    private String destiny;

    public OrderListItem() {
    }

    public OrderListItem(String id, String car, String customer, String lastStatus, String origin, String destiny) {
        this.id = id;
        this.car = car;
        this.customer = customer;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
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
