package pl.szymanski.paker.models;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "routes")
public class Route {

    @Id
    private String id;

    private double distance;

    @DBRef
    private ArrayList<City> route = new ArrayList<City>();

    

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

    public ArrayList<City> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<City> route) {
        this.route = route;
    }

    public Route() {
    }

    public Route(double distance, ArrayList<City> route) {
        this.distance = distance;
        this.route = route;
    }
}
