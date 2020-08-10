package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.Role;
import pl.szymanski.paker.models.User;
import pl.szymanski.paker.payload.request.UserChangePasswordRequest;
import pl.szymanski.paker.payload.request.UserRequest;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.UserResponse;
import pl.szymanski.paker.repository.RoleRepo;
import pl.szymanski.paker.repository.UserRepo;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepo user_R;
    @Autowired
    private RoleRepo role_R;
    @Autowired
    private PasswordEncoder ncdr;
    public ResponseEntity<?> findAll(){
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findAll()) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findById(String id){
        Optional<User> userOptional = user_R.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponse response = userToUserResponse(user);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByName(String name) {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByNameRegex(name)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findBySurname(String surname) {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findBySurnameRegex(surname)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByUsername(String username) {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByUsernameRegex(username)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByEmail(String email) {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByEmailRegex(email)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByFree(Boolean isFree) {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByIsFree(isFree)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }
    public ResponseEntity<?> findByLocalization(String localization) {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findByLocalization(localization)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByNumber(String number) {
        Optional<User> userOptional = user_R.findByNumber(number);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponse response = userToUserResponse(user);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> addUser(UserRequest newUser) {
        if (newUser.isValid()) {

            User user = userRequestToUser(newUser);
            if (user_R.existsByUsername(user.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("User exist"));
            }
            if (user_R.existsByEmail(user.getEmail())) {
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

    public ResponseEntity<?> delUser(String id) {
        if (user_R.existsById(id)) {
            user_R.deleteById(id);
            return ResponseEntity
                    .ok(new MessageResponse("Deleted"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    public ResponseEntity<?> updatePassword(UserChangePasswordRequest passwordRequest) {

        if (passwordRequest.isValid()) {
            if (user_R.existsById(passwordRequest.getId())) {
                Optional<User> userOptional = user_R.findById(passwordRequest.getId());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if (ncdr.matches(passwordRequest.getOldPassword(), user.getPassword())) {
                        user.setPassword(ncdr.encode(passwordRequest.getPassword()));
                        user_R.save(user);
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Password changed"));
                    }
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Old password doesn't match"));
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

    public ResponseEntity<?> updateUser(UserRequest updatedUser) {
        if (updatedUser.isValid()) {
            User user = userRequestToUser(updatedUser);
            Optional<User> toUpdateOptional = user_R.findByUsername(updatedUser.getUserName());
            if (toUpdateOptional.isPresent()) {
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

    private User userRequestToUser(UserRequest user) {
        User nowy = new User(user.getUserName(), user.getEmail(), ncdr.encode(user.getPassword()));
        Set<Role> tmp = new HashSet<Role>();
        for (String role : user.getRoles()) {
            tmp.add(role_R.findByName(role));
        }
        nowy.setRoles(tmp);
        return nowy;
    }

    private UserResponse userToUserResponse(User userObj) {
        UserResponse user = new UserResponse(userObj.getId(), userObj.getUsername(), userObj.getName(), userObj.getSurname(), userObj.getNumber(), userObj.getEmail(), userObj.getLocalization(), userObj.isFree());
        Set<String> tmp = new HashSet<String>();
        for (Role role : userObj.getRoles()) {
            tmp.add(role.getName().name());
        }
        user.setRoles(tmp);
        return user;
    }
}
