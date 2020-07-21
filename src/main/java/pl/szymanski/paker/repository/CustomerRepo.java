package pl.szymanski.paker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.szymanski.paker.models.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
