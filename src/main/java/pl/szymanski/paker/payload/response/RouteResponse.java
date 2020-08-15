package pl.szymanski.paker.payload.response;

import java.util.ArrayList;
import java.util.List;

public class RouteResponse {
    private String id;
    private double distance;
    List<String> route = new ArrayList<String>();

    public RouteResponse() {
    }

    public RouteResponse(String id, double distance, List<String> route) {
        this.id = id;
        this.distance = distance;
        this.route = route;
    }

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

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }
}
