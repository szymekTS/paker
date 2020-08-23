package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.models.Role;
import pl.szymanski.paker.models.User;
import pl.szymanski.paker.payload.request.UserChangeLocalization;
import pl.szymanski.paker.payload.request.UserChangePasswordRequest;
import pl.szymanski.paker.payload.request.UserRequest;
import pl.szymanski.paker.payload.request.UserUpdate;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.UserResponse;
import pl.szymanski.paker.repository.RoleRepo;
import pl.szymanski.paker.repository.UserRepo;
import pl.szymanski.paker.services.UserService;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("find_all")
    public ResponseEntity<?> findAll() {
        return userService.findAll();
    }

    @GetMapping("find")
    public ResponseEntity<?> findById(@RequestParam String id) {
        return userService.findById(id);
    }

    @GetMapping("find_name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @GetMapping("find_surname")
    public ResponseEntity<?> findBySurname(@RequestParam String surname) {
        return userService.findBySurname(surname);
    }

    @GetMapping("find_username")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("find_email")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("find_free")
    public ResponseEntity<?> findByFree(@RequestParam Boolean isFree) {
        return userService.findByFree(isFree);
    }

    @GetMapping("find_localization")
    public ResponseEntity<?> findByLocalization(@RequestParam String localization) {
        return userService.findByLocalization(localization);
    }

    @GetMapping("find_number")
    public ResponseEntity<?> findByNumber(@RequestParam String number) {
        return userService.findByNumber(number);
    }

    @PostMapping("new")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest newUser) {
        return userService.addUser(newUser);
    }

    @DeleteMapping("del")
    public ResponseEntity<?> delUser(@RequestParam String id) {
        return userService.delUser(id);
    }

    @PostMapping("pass")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UserChangePasswordRequest passwordRequest) {
        return userService.updatePassword(passwordRequest);
    }

    @PostMapping("loc")
    public ResponseEntity<?> updateLocalization(@RequestBody UserChangeLocalization localization) {
        return userService.updateLocalization(localization);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdate updatedUser) {
        return userService.updateUser(updatedUser);
    }
}

