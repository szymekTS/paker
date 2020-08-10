package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.szymanski.paker.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(String id);

    Optional<User> findByNumber(String number);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "{'username': {$regex : ?0, $options: 'i'}}")
    List<User> findByUsernameRegex(String username);

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<User> findByNameRegex(String name);

    @Query(value = "{'surname': {$regex : ?0, $options: 'i'}}")
    List<User> findBySurnameRegex(String surname);

    @Query(value = "{'email': {$regex : ?0, $options: 'i'}}")
    List<User> findByEmailRegex(String email);


    void deleteById(String id);

    List<User> findByIsFree(Boolean isFree);

    List<User> findByLocalization(String localization);
}
