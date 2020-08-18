package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.Status;
import pl.szymanski.paker.models.User;

import java.util.List;

public interface StatusRepo extends MongoRepository<Status, String> {

    List<Status> findByWorker(User worker);

    List<Status> findByStatusCode(String status);
}