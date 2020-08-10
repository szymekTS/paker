package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.Order;

public interface OrderRepo extends MongoRepository<Order, String> {
}
