package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.CargoCheckType;
import pl.szymanski.paker.payload.request.CargoRequest;
import pl.szymanski.paker.payload.response.OrderListItem;
import pl.szymanski.paker.services.CargoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cargo/")
public class CargoController {
    @Autowired
    CargoService cargoService;

    @GetMapping("find_all")
    public ResponseEntity<?> findAll() {
        return cargoService.findAll();
    }

    @GetMapping("find_value")
    public ResponseEntity<?> findByValueMinMax(@RequestParam float min, @RequestParam float max) {
        return cargoService.findByValue(min, max);
    }

    @GetMapping("find_weight")
    public ResponseEntity<?> findByWeightMinMax(@RequestParam float min, @RequestParam float max) {
        return cargoService.findByWeight(min,max);
    }
    @PostMapping("check_type")
    public ResponseEntity<?> checkPrefferedCarType(@RequestBody CargoCheckType listItem) {
        return cargoService.checkType(listItem);
    }

    @GetMapping("find")
    public ResponseEntity<?> findById(@RequestParam String id) {
        return cargoService.findById(id);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody CargoRequest cargoRequest) {
        return cargoService.update(cargoRequest);
    }

    @PostMapping("new")
    public ResponseEntity<?> addCargo(@RequestBody CargoRequest cargoRequest) {
        return cargoService.addNew(cargoRequest);
    }

}
