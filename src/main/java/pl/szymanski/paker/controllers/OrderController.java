package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.OrderRequest;
import pl.szymanski.paker.repository.OrderRepo;
import pl.szymanski.paker.services.OrderService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("find_all")
    public ResponseEntity<?> findAll() {
        return orderService.findAll();
    }

    @GetMapping("find")
    public ResponseEntity<?> findById(@RequestParam String id) {
        return orderService.findByID(id);
    }

    @GetMapping("find_car")
    public ResponseEntity<?> findByCar(@RequestParam String car) {
        return orderService.findByCar(car);
    }

    @GetMapping("find_cargo_value")
    public ResponseEntity<?> findByCargoValue(@RequestParam float min, @RequestParam float max) {
        return orderService.findByCargoValue(min,max);
    }
    @GetMapping("find_cargo_weight")
    public ResponseEntity<?> findByCargoWeigh(@RequestParam float min, @RequestParam float max) {
        return orderService.findByCargoWeight(min, max);
    }

    @GetMapping("find_customer")
    public ResponseEntity<?> findByCustomer(@RequestParam String id) {
        return orderService.findByCustomer(id);
    }

    @GetMapping("find_status")
    public ResponseEntity<?> findByStatus(@RequestParam String status) {
        return orderService.findByStatus(status);
    }
    @GetMapping("find_origin")
    public ResponseEntity<?> findByOrigin(@RequestParam String origin) {
        return orderService.findByOrigin(origin);
    }
    @GetMapping("find_destiny")
    public ResponseEntity<?> findByDestiny(@RequestParam String destiny) {
        return orderService.findByDestiny(destiny);
    }
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody OrderRequest orderRequest) {
        return orderService.update(orderRequest);
    }
    @DeleteMapping("del")
    public ResponseEntity<?> del(@RequestParam String id) {
        return orderService.del(id);
    }

    @PostMapping("new")
    public ResponseEntity<?> addNew(@RequestBody OrderRequest orderRequest){
        return orderService.addNew(orderRequest);
    }
}
