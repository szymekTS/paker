package pl.szymanski.paker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import pl.szymanski.paker.models.enums.ECarType;

@Document(collection = "car_types")
public class CarType {
    @Id
    private String id;
    
    private ECarType type;

    public CarType() {
    }

    public CarType(ECarType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ECarType getType() {
        return type;
    }

    public void setType(ECarType type) {
        this.type = type;
    }

}
