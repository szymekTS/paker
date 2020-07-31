package pl.szymanski.paker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.szymanski.paker.models.Role;
import pl.szymanski.paker.models.enums.ERole;

import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
