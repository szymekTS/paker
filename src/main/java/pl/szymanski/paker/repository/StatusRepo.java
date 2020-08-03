package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.Status;

public interface StatusRepo extends MongoRepository<Status, String> {

}