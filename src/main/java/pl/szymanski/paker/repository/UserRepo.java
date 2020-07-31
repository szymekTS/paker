package pl.szymanski.paker.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.szymanski.paker.models.User;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
