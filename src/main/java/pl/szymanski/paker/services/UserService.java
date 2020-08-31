package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.City;
import pl.szymanski.paker.models.Role;
import pl.szymanski.paker.models.User;
import pl.szymanski.paker.payload.request.UserChangeLocalization;
import pl.szymanski.paker.payload.request.UserChangePasswordRequest;
import pl.szymanski.paker.payload.request.UserRequest;
import pl.szymanski.paker.payload.request.UserUpdate;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.UserResponse;
import pl.szymanski.paker.repository.CityRepo;
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
    private CityRepo city_R;

    @Autowired
    private PasswordEncoder ncdr;

    public ResponseEntity<?> findAll() {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findAll()) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findById(String id) {
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

    public ResponseEntity<?> findDriverLoc(String localization) {
        List<UserResponse> responseList = new ArrayList<UserResponse>();

        for (User u : user_R.findDriverByFreeAndLocalization(true,true,localization)) {
            responseList.add(userToUserResponse(u));
        }
        return ResponseEntity.ok(responseList);
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
                                .ok(new MessageResponse("Hasło zmienione"));
                    }
                    return ResponseEntity
                            .ok(new MessageResponse("Hasła nie pasują"));
                }
            }
            return ResponseEntity
                    .ok(new MessageResponse("Nie znaleziono"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Błędne zapytanie"));
    }

    public ResponseEntity<?> updateUser(UserUpdate updatedUser) {
        if (updatedUser.isValid()) {
            User user = userUpdateToUser(updatedUser);
            Optional<User> toUpdateOptional = user_R.findByUsername(updatedUser.getUserName());
            if (toUpdateOptional.isPresent()) {
                User toUpdate = toUpdateOptional.get();
                toUpdate.setName(user.getName());
                toUpdate.setSurname(user.getSurname());
                toUpdate.setNumber(user.getNumber());
                toUpdate.setDriver(user.isDriver());
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

    public ResponseEntity<?> updateLocalization(UserChangeLocalization localization) {
        System.out.println(localization.getId()+" "+localization.getNewLocalization());
        Optional<User> optionalUser = user_R.findById(localization.getId());
        if (optionalUser.isPresent()) {
            System.out.println("user optional present");
            Optional<City> cityOptional = city_R.findById(localization.getNewLocalization());
            if(cityOptional.isPresent()){
                System.out.println("city optional present");
                User user = optionalUser.get();
                System.out.println(user.getUsername());
                City city = cityOptional.get();
                System.out.println(city.getName());

                user.setLocalization(city.getId());
                user_R.save(user);
                return ResponseEntity
                        .ok()
                        .body(new MessageResponse("ok"));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("City not found"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("User not found"));
    }

    private User userRequestToUser(UserRequest user) {
        User nowy = new User(user.getUserName(), user.getEmail(), ncdr.encode(user.getPassword()));
        Set<Role> tmp = new HashSet<Role>();
        for (String role : user.getRoles()) {
            tmp.add(role_R.findByName(role));
        }
        nowy.setName(user.getName());
        nowy.setSurname(user.getSurname());
        nowy.setNumber(user.getNumber());
        nowy.setLocalization(user.getLocalization());
        nowy.setFree(user.getIsFree());

        nowy.setDriver(user.getIsDriver());


        nowy.setRoles(tmp);
        return nowy;
    }

    private UserResponse userToUserResponse(User userObj) {
        UserResponse user = new UserResponse();

        user.setId(userObj.getId());
        user.setUserName(userObj.getUsername());
        user.setName(userObj.getName());
        user.setSurname(userObj.getSurname());
        user.setNumber(userObj.getNumber());
        user.setEmail(userObj.getEmail());
        user.setLocalization(userObj.getLocalization());
        user.setFree(userObj.isFree());
        user.setDriver(userObj.isDriver());
        Set<String> tmp = new HashSet<String>();
        for (Role role : userObj.getRoles()) {
            tmp.add(role.getName().name());
        }
        user.setRoles(tmp);
        return user;
    }


    private User userUpdateToUser(UserUpdate user) {
        User nowy = new User();
        Set<Role> tmp = new HashSet<Role>();
        for (String role : user.getRoles()) {
            tmp.add(role_R.findByName(role));
        }
        nowy.setUsername(user.getUserName());
        nowy.setName(user.getName());
        nowy.setSurname(user.getSurname());
        nowy.setNumber(user.getNumber());
        nowy.setEmail(user.getEmail());
        nowy.setLocalization(user.getLocalization());
        nowy.setDriver(user.getIsDriver());

        nowy.setRoles(tmp);
        return nowy;
    }



}
