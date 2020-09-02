package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.MaintenenceRequest;
import pl.szymanski.paker.services.MaintenanceService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maintanence/")
public class MaintenenceController {
    @Autowired
    MaintenanceService maintanenceService;

    @GetMapping("find_all")
    public ResponseEntity<?> findAll() {
        return maintanenceService.findAll();
    }

    @GetMapping("find")
    public ResponseEntity<?> findById(@RequestParam String id) {
        return maintanenceService.findById(id);
    }

    @GetMapping("find_car")
    public ResponseEntity<?> findByCar(@RequestParam String car) {
        return maintanenceService.findByCar(car);
    }
    @GetMapping("find_status")
    public ResponseEntity<?> findByStatus(@RequestParam String status) {
        return maintanenceService.findByStatus(status);
    }

    @PostMapping("new")
    public ResponseEntity<?> addCar(@Valid @RequestBody MaintenenceRequest maintenenceRequest) {
        return maintanenceService.addMaintenance(maintenenceRequest);
    }

    @DeleteMapping("del")
    public ResponseEntity<?> delCar(@RequestParam String id) {
        return maintanenceService.delMaintenance(id);
    }

    @GetMapping("change_status")
    public ResponseEntity<?> changeStatus(@RequestParam  String id) {
        return maintanenceService.changeStatus(id);
    }
}
