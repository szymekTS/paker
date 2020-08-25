package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.CustomerRequest;
import pl.szymanski.paker.services.CustomerService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer/")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("find_all")
    public ResponseEntity<?> findAll() {
        return customerService.findAll();
    }

    @GetMapping("find")
    public ResponseEntity<?> findById(@RequestParam String id) {
        return customerService.findById(id);
    }

    @GetMapping("find_name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        return customerService.findByName(name);
    }

    @GetMapping("find_surname")
    public ResponseEntity<?> findBySurname(@RequestParam String surname) {
        return customerService.findBySurname(surname);
    }

    @GetMapping("find_email")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        return customerService.findByEmail(email);
    }

    @PostMapping("new")
    public ResponseEntity<?> addUser2(@Valid @RequestBody CustomerRequest newUser) {
        return customerService.addCustomer(newUser);
    }

    @DeleteMapping("del")
    public ResponseEntity<?> delUser(@RequestParam String id) {
        return customerService.delUser(id);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody CustomerRequest updatedUser) {
        return customerService.updateUser(updatedUser);
    }
}
