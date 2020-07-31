package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.Route;

public interface RouteRepo extends MongoRepository<Route, String> {

}