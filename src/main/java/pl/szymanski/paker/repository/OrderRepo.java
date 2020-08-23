package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.*;
import pl.szymanski.paker.payload.request.CargoRequest;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends MongoRepository<Order, String> {
    List<Order> findByCar(Car car);

    Optional<Order> findByCargo(Cargo cargo);

    List<Order> findByCustomer(Customer customer);
    List<Order> findByLastStatus(String status);

    List<Order> findByOrigin(City city);
    List<Order> findByDestiny(City city);

    List<Order> findByLocalization(String localization);
}
