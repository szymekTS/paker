package pl.szymanski.paker.payload.response;

import java.util.Map;

public class NeighbourCityResponse {
    private String cityId;
    private Map<String, Double> neighbours;
    private Map<String, String> names;

    public NeighbourCityResponse() {

    }

    public NeighbourCityResponse(String cityId, Map<String, Double> neighbours, Map<String, String> names) {
        this.cityId = cityId;
        this.neighbours = neighbours;
        this.names = names;
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

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }
}
