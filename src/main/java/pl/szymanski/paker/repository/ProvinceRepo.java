package pl.szymanski.paker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.szymanski.paker.models.Province;

public interface ProvinceRepo extends JpaRepository<Province, Long> {

}
