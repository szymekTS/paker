package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.CarType;
import pl.szymanski.paker.models.enums.ECarType;

import java.util.Optional;

public interface CarTypeRepo extends MongoRepository<CarType, String> {

    Optional<CarType> findByType(ECarType type);
    CarType findByType(String type);
}