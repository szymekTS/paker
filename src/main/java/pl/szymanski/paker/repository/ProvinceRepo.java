package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.Province;
import pl.szymanski.paker.models.enums.EProvince;

public interface ProvinceRepo extends MongoRepository<Province, String> {
    public Province findByName(EProvince prov);
    public Province findByName(String name);
}
