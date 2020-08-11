package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.CargoRequest;
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
