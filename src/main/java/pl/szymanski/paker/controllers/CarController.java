package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.CarRequest;
import pl.szymanski.paker.services.CarService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/car/")
public class CarController {

    @Autowired
    CarService carService;


    @GetMapping("find_all")
    public ResponseEntity<?> findAll() {
        return carService.findAll();
    }

    @GetMapping("find")
    public ResponseEntity<?> findById(@RequestParam String id) {
        return carService.findByID(id);
    }

    @GetMapping("find_brand")
    public ResponseEntity<?> findByBrand(@RequestParam String brand) {
        return carService.findByBrand(brand);
    }

    @GetMapping("find_model")
    public ResponseEntity<?> findByModel(@RequestParam String model) {
        return carService.findByModel(model);
    }

    @GetMapping("find_plate")
    public ResponseEntity<?> findByPlate(@RequestParam String plate) {
        return carService.findByPlate(plate);
    }

    @GetMapping("find_type")
    public ResponseEntity<?> findByType(@RequestParam String type) {
        return carService.findByType(type);
    }

    @PostMapping("new")
    public ResponseEntity<?> addCar(@Valid @RequestBody CarRequest newCar) {
        return carService.addNew(newCar);
    }

    @DeleteMapping("del")
    public ResponseEntity<?> delCar(@RequestParam String id) {
        return carService.del(id);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateCar(@Valid @RequestBody CarRequest updateCar) {
        return carService.update(updateCar);
    }
}

