package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szymanski.paker.models.*;
import pl.szymanski.paker.models.enums.ECarType;
import pl.szymanski.paker.models.enums.EProvince;
import pl.szymanski.paker.models.enums.ERole;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.repository.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/db/")
public class DBLoaderController {

    @Autowired
    private RoleRepo role_R;
    @Autowired
    private ProvinceRepo prov_R;
    @Autowired
    private MaintenanceRepo main_R;
    @Autowired
    private CarRepo car_R;
    @Autowired
    private CarTypeRepo carType_R;
    @Autowired
    private UserRepo user_R;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CityRepo city_R;

    @GetMapping("load_admin")
    public ResponseEntity<MessageResponse> initiateAdmin(){

        if (user_R.existsByUsername("admin")) {
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Admin is already in DB!"));
        }
        else{
            User admin =  new User("admin","tomaszekszym@gmail.com","Tomasz","Szymański", encoder.encode("admin"),"662527971");

            Set<Role> roles = new HashSet<>();
            Role adminRole = role_R.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            Role modRole = role_R.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            roles.add(modRole);

            admin.setRoles(roles);
            user_R.save(admin);
        }
        return ResponseEntity
                .ok()
                .body(new MessageResponse("Admin loaded !"));
    }

    @GetMapping("load_role")
    public String initiateRole(){
        role_R.deleteAll();

        Role role;
        var roles = ERole.values();
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

    @GetMapping("load_repair")
    public String initiateRepair(){
        role_R.deleteAll();

        Role role;
        var roles = ERole.values();
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

    @GetMapping("load_province")
    public String initiateProvince(){
        prov_R.deleteAll();

        Province province;
        var prov= EProvince.values();
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

    @GetMapping("load_cartype")
    public String initiateCarTypes(){
        carType_R.deleteAll();

        CarType carType;
        var carTypes= ECarType.values();
        for(ECarType type: carTypes){
            carType = new CarType(type);
            carType_R.save(carType);
        }
        StringBuilder out = new StringBuilder();
        for (Role roleE : role_R.findAll()) {
            out.append(roleE.getName());
            out.append("\n");
        }

        return "Baza typów aut załadowana\n" + out;
    }

    @GetMapping("load_cities")
    public String initiateCities(){
        city_R.deleteAll();

        List<City> cityList = new ArrayList<City>();

        cityList.add(new City("Kielce", "25000", prov_R.findByName(EProvince.PROV_SWIETOKRZYSKIE)));
        cityList.add(new City("Warszawa", "00000", prov_R.findByName(EProvince.PROV_MAZOWIECKIE)));



        city_R.saveAll(cityList);

        StringBuilder out = new StringBuilder();
        for (City city : city_R.findAll()) {
            out.append(city.getName());
            out.append("\n");
        }

        return "Baza typów aut załadowana\n" + out;
    }



}
