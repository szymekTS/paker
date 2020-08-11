package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.szymanski.paker.models.Customer;
import pl.szymanski.paker.models.User;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends MongoRepository<Customer, String> {
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Customer> findByNameRegex(String name);

    @Query(value = "{'surname': {$regex : ?0, $options: 'i'}}")
    List<Customer> findBySurnameRegex(String surname);

    @Query(value = "{'email': {$regex : ?0, $options: 'i'}}")
    List<Customer> findByEmailRegex(String email);

    Boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}
