package pl.szymanski.paker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.szymanski.paker.models.enums.EProvince;

@Document(collection = "provinces")
public class Province {

    @Id
    private String id;

    private EProvince name;

    public Province() {
        super();
    }

    public Province(EProvince name) {
        super();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EProvince getName() {
        return name;
    }

    public void setName(EProvince name) {
        this.name = name;
    }


}
