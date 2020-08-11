package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.CityRequest;
import pl.szymanski.paker.services.CityService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/city/")
public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping("/find")
    public ResponseEntity<?> getCityById(@RequestParam String id) {
        return cityService.findByID(id);
    }

    @GetMapping("/find_name")
    public ResponseEntity<?> getCityByName(@RequestParam String name) {
        return cityService.findByName(name);
    }

    @GetMapping("/find_zip")
    public ResponseEntity<?> getCityByZipCode(@RequestParam String zipCode) {
        return cityService.findByZipCode(zipCode);
    }

    @GetMapping("/find_prov")
    public ResponseEntity<?> getCityByProvince(@RequestParam String prov) {
        return cityService.findByProvince(prov);
    }


    @GetMapping("/find_all")
    public ResponseEntity<?> getCityAll() {
        return cityService.findAll();
    }

    @GetMapping("/get_neighbours")
    public ResponseEntity<?> getCityNeighbours(@RequestParam String id) {
        return cityService.findNeighbours(id);
    }

    @GetMapping("/add_neighbour")
    public ResponseEntity<?> addNeighbour(@RequestParam String id, @RequestParam String city_id, @RequestParam double distance) {
        return cityService.addNeighbour(id, city_id, distance);
    }

    @PostMapping("/new")
    public ResponseEntity<?> addCity(@Valid @RequestBody CityRequest newCity) {
        return cityService.addCity(newCity);
    }

    @DeleteMapping("/del")
    public ResponseEntity<?> delCity(@RequestParam String id) {
        return cityService.delCity(id);
    }

}
