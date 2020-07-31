package pl.szymanski.paker.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import pl.szymanski.paker.models.enums.ECartype;

@Document(collection = "car_types")
public class CarType {
    @Id
    private String id;
    
    private ECartype type;

    public CarType() {
    }

    public CarType(ECartype type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ECartype getType() {
        return type;
    }

    public void setType(ECartype type) {
        this.type = type;
    }

}
