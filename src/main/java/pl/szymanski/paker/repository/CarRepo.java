package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.Car;

public interface CarRepo extends MongoRepository<Car, String> {

}