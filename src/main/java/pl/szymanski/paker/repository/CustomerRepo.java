package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.Customer;

public interface CustomerRepo extends MongoRepository<Customer, Long> {

}
