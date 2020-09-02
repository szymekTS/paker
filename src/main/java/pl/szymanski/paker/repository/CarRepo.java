package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import pl.szymanski.paker.models.Car;
import pl.szymanski.paker.models.CarType;
import pl.szymanski.paker.models.enums.ECarType;

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

    List<Car> findByLocalization(String localization);

    @Query(value = "{'localization': ?0, 'inRepair': ?1}")
    List<Car> findBrokenCarInLoc(String localization,Boolean inRepair);

    @Query(value = "{'localization': ?0, 'type.id': ?1, 'inRepair': ?2, 'isFree': ?3}")
    List<Car> findGoodFreeCarInLoc(String localization, String type, Boolean inRepair, Boolean isFree);
}