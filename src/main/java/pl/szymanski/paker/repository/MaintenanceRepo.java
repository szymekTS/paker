package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.Car;
import pl.szymanski.paker.models.Maintenance;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepo extends MongoRepository<Maintenance, String> {

    List<Maintenance> findByCar(Car car);

    List<Maintenance> findByStatus(String status);
}