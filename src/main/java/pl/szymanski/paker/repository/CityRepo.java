package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.szymanski.paker.models.City;
import pl.szymanski.paker.models.Province;

import java.util.List;
import java.util.Optional;

public interface CityRepo extends MongoRepository<City, String> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<City> findByNameRegex(String name);

    List<City> findByZipCode(String zipCode);

    List<City> findByProvince(Province prov);

    Optional<City> findById(String id);

    Boolean  existsByZipCode(String zipCode);

    void deleteById(String id);
}
