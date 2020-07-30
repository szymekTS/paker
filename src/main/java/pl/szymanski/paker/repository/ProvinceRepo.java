package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.Province;

public interface ProvinceRepo extends MongoRepository<Province, Long> {

}
