package pl.szymanski.paker.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.Cargo;

public interface CargoRepo extends MongoRepository<Cargo, String> {

}