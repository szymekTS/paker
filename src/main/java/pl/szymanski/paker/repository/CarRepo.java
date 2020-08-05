package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import pl.szymanski.paker.models.Car;
import pl.szymanski.paker.models.CarType;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends MongoRepository<Car, String> {
    @Query(value = "{'brand': {$regex : ?0, $options: 'i'}}")
    List<Car> findByBrandRegex(String name);

    @Query(value = "{'model': {$regex : ?0, $options: 'i'}}")
    List<Car> findByModelRegex(String model);

    @Query(value = "{'licensePlate': {$regex : ?0, $options: 'i'}}")
    List<Car> findByLicensePlateRegex(String plate);

    List<Car> findByType(CarType carType);

    Optional<Car> findByLicensePlate(String plate);

    boolean existsByLicensePlate(String licensePlate);
}