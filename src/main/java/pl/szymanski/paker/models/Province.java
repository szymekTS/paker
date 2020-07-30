package pl.szymanski.paker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.szymanski.paker.models.enums.EProvince;

@Document(collation = "provinces")
public class Province {

    @Id
    private Integer id;

    private EProvince name;

    public Province() {
        super();
    }

    public Province(EProvince name) {
        super();
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EProvince getName() {
        return name;
    }

    public void setName(EProvince name) {
        this.name = name;
    }


}
