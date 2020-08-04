package pl.szymanski.paker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.models.City;
import pl.szymanski.paker.models.Role;
import pl.szymanski.paker.models.User;
import pl.szymanski.paker.payload.request.UserChangePasswordRequest;
import pl.szymanski.paker.payload.request.UserRequest;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.UserResponse;
import pl.szymanski.paker.repository.RoleRepo;
import pl.szymanski.paker.repository.UserRepo;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserRepo user_R;
    @Autowired
    private RoleRepo role_R;
    @Autowired
    private PasswordEncoder ncdr;

    @GetMapping("find_all")
    public ResponseEntity<?> findAll(){
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findAll()) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("find")
    public ResponseEntity<?> findById(@RequestParam String id){
        Optional<User> userOptional = user_R.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserResponse response = userToUserResponse(user);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    @GetMapping("find_name")
    public ResponseEntity<?> findByName(@RequestParam String name){
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByNameRegex(name)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("find_surname")
    public ResponseEntity<?> findBySurname(@RequestParam String surname){
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findBySurnameRegex(surname)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("find_username")
    public ResponseEntity<?> findByUsername(@RequestParam String username){
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByUsernameRegex(username)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("find_email")
    public ResponseEntity<?> findByemail(@RequestParam String email){
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByEmailRegex(email)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("find_number")
    public ResponseEntity<?> findByNumber(@RequestParam String number){
        Optional<User> userOptional = user_R.findByNumber(number);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserResponse response = userToUserResponse(user);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    @PostMapping("new")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest newUser){
        if(newUser.isValid()){

            User user = userRequestToUser(newUser);
            if(user_R.existsByUsername(user.getUsername())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("User exist"));
            }
            if(user_R.existsByEmail(user.getEmail())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email exist"));
            }

            user_R.save(user);

            return ResponseEntity.ok(userToUserResponse(user));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    @DeleteMapping("del")
    public ResponseEntity<?>delUser(@RequestParam String id){
        if(user_R.existsById(id)){
            user_R.deleteById(id);
            return ResponseEntity
                    .ok(new MessageResponse("Deleted"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    @PostMapping("pass")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UserChangePasswordRequest passwordRequest){

        if(passwordRequest.isValid()){
            if(user_R.existsById(passwordRequest.getId())){
                Optional<User> userOptional = user_R.findById(passwordRequest.getId());
                if(userOptional.isPresent()){
                    User user = userOptional.get();
                    if(ncdr.matches(passwordRequest.getOldPassword(), user.getPassword())){
                        user.setPassword(ncdr.encode(passwordRequest.getPassword()));
                        user_R.save(user);
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Password changed"));
                    }
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Old password doesnt match"));
                }
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Not found"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    @PostMapping("update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest updatedUser){
        if(updatedUser.isValid()){
            User user = userRequestToUser(updatedUser);
            Optional<User> toUpdateOptional = user_R.findByUsername(updatedUser.getUserName());
            System.out.println(user);
            if(toUpdateOptional.isPresent()){
                User toUpdate = toUpdateOptional.get();
                toUpdate.setName(user.getName());
                toUpdate.setSurname(user.getSurname());
                toUpdate.setNumber(user.getNumber());
                toUpdate.setRoles(user.getRoles());
                user_R.save(toUpdate);
                return ResponseEntity.ok(userToUserResponse(toUpdate));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Not found"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    private User userRequestToUser(UserRequest user){
        User nowy = new User(user.getUserName(),user.getEmail(),ncdr.encode(user.getPassword()));
        Set<Role> tmp = new HashSet<Role>();
        for (String  role : user.getRoles()) {
            tmp.add(role_R.findByName(role));
        }
        nowy.setRoles(tmp);
        return nowy;
    }

    private UserResponse userToUserResponse(User userObj) {
        UserResponse user = new UserResponse(userObj.getId(), userObj.getUsername(), userObj.getName(), userObj.getSurname(), userObj.getNumber(), userObj.getEmail());
        Set<String> tmp = new HashSet<String>();
        for (Role role : userObj.getRoles()) {
            tmp.add(role.getName().name());
        }
        user.setRoles(tmp);
        return user;
    }
}

