package pl.szymanski.paker.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.Cargo;

import java.util.List;

public interface CargoRepo extends MongoRepository<Cargo, String> {

    List<Cargo> findByValueBetween(float low, float high);

    List<Cargo> findByWeightBetween(float low, float high);
}