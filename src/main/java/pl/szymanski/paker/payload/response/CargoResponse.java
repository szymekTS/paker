package pl.szymanski.paker.payload.response;

import pl.szymanski.paker.models.Item;

import java.util.HashSet;
import java.util.Set;

public class CargoResponse {
    private String id;
    private Set<Item> register = new HashSet<Item>();
    private float value;
    private float weight;

    public CargoResponse() {
    }

    public CargoResponse(String id, Set<Item> register, float value, float weight) {
        this.id = id;
        this.register = register;
        this.value = value;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Item> getRegister() {
        return register;
    }

    public void setRegister(Set<Item> register) {
        this.register = register;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
