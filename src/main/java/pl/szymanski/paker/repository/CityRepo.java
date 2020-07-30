package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.City;

public interface CityRepo extends MongoRepository<City, Long> {

}
