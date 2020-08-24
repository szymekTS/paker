package pl.szymanski.paker.payload.request;

public class CityAddNeighbour {
    String id;
    String city_id;
    Double distance;

    public CityAddNeighbour() {
    }

    public CityAddNeighbour(String id, String city_id, Double distance) {
        this.id = id;
        this.city_id = city_id;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
