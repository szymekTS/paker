package pl.szymanski.paker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.szymanski.paker.models.ERole;
import pl.szymanski.paker.models.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
