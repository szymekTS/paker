package pl.szymanski.paker.payload.response;

import java.util.Map;

public class NeighbourCityResponse {
    private String cityId;
    private Map<String, Double> neighbours;

    public NeighbourCityResponse() {

    }

    public NeighbourCityResponse(String city, Map<String, Double> neighbours) {
        this.cityId = city;
        this.neighbours = neighbours;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Map<String, Double> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Map<String, Double> neighbours) {
        this.neighbours = neighbours;
    }
}
