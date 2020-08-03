package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.Maintenance;

public interface MaintenanceRepo extends MongoRepository<Maintenance, String> {

}