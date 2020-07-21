package pl.szymanski.paker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.szymanski.paker.models.City;

public interface CityRepo extends JpaRepository<City, Long> {

}
