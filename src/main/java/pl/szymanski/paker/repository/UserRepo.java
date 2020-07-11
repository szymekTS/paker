package pl.szymanski.paker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.szymanski.paker.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
