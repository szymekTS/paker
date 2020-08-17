package pl.szymanski.paker.models;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "routes")
public class Route {

    @Id
    private String id;

    private double distance;

    @DBRef
    private List<City> route = new ArrayList<>();



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<City> getRoute() {
        return route;
    }

    public void setRoute(List<City> route) {
        this.route = route;
    }

    public Route() {
    }

    public Route(double distance, List<City> route) {
        this.distance = distance;
        this.route = route;
    }
}
