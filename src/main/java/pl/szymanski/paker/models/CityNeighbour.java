package pl.szymanski.paker.models;

public class CityNeighbour {
    private String id;
    private double distance;

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

    public CityNeighbour(String id, double distance) {
        this.id = id;
        this.distance = distance;
    }

    public CityNeighbour() {
    }
}
