package pl.szymanski.paker.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cargos")
public class Cargo {

    @Id
    private String id;
    private Set<Item> register = new HashSet<Item>();

    private float value;
    private float weight;

    public Cargo() {
    }

    public Cargo(Set<Item> register) {
        this.register = register;
        calculateValueAndWeight();
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

    public float getWeight() {
        return weight;
    }

    private void calculateValueAndWeight() {
        this.weight = 0.0f;
        this.value = 0.0f;

        for (Item item : this.register) {
            this.weight += item.getWeight();
            this.value += item.getValue();
        }
    }
}
