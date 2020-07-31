package pl.szymanski.paker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.szymanski.paker.models.Province;
import pl.szymanski.paker.models.Role;
import pl.szymanski.paker.models.enums.EProvince;
import pl.szymanski.paker.models.enums.ERole;
import pl.szymanski.paker.repository.ProvinceRepo;
import pl.szymanski.paker.repository.RoleRepo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
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

	@Autowired
	private RoleRepo role_R;
	@Autowired
	private ProvinceRepo prov_R;

	@GetMapping("/load1")
	public String initiateRole(){
		role_R.deleteAll();

		Role role;
		ERole roles[] = ERole.values();
		for(ERole rolee: roles){
			role = new Role(rolee);
			role_R.save(role);
		}
		StringBuilder out = new StringBuilder();
		for (Role roleE : role_R.findAll()) {
			out.append(roleE.getName());
			out.append("\n");
		}

		return "Baza ról załadowana\n" + out;
	}

	@GetMapping("/load2")
	public String initiateDB(){
		prov_R.deleteAll();

		Province province;
		EProvince prov[] = EProvince.values();
		for (EProvince prove : prov) {
			province = new Province(prove);
			prov_R.save(province);
		}

		StringBuilder out = new StringBuilder();
		for (Province provE : prov_R.findAll()) {
			out.append(provE.getName());
			out.append("\n");
		}

		return "Baza województw załadowana\n" + out;
	}
}