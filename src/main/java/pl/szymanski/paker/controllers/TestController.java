package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.szymanski.paker.models.Car;
import pl.szymanski.paker.models.CarType;
import pl.szymanski.paker.models.Maintenance;
import pl.szymanski.paker.models.enums.ECarType;
import pl.szymanski.paker.repository.CarRepo;
import pl.szymanski.paker.repository.CarTypeRepo;
import pl.szymanski.paker.repository.MaintenanceRepo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	private CarTypeRepo carType_R;
	@Autowired
	private CarRepo car_R;
	@Autowired
	private MaintenanceRepo main_R;

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/main")
	public String testMaintenance(){
		CarType typ = carType_R.findByType(ECarType.TYPE_SMALL)
					.orElseThrow(() -> new RuntimeException("Error: CarType is not found."));
		Car auto = new Car("Fiat", "Doblo", "TOP1234", typ);
		car_R.save(auto);
		Maintenance naprawa = new Maintenance(auto, "Jebła uszczelka pod głowicą");
		main_R.save(naprawa);
		return "ok";
	}
}