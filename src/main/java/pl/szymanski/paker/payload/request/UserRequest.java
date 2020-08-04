package pl.szymanski.paker.payload.request;

import pl.szymanski.paker.models.Role;

import java.util.HashSet;
import java.util.Set;

public class UserRequest {
    private String userName;
    private String name;
    private String surname;
    private String number;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();

    public UserRequest() {
    }

    public UserRequest(String userName, String name, String surname, String number, String email, String password) {
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isValid() {
        if(this.userName.length()<6)
            return false;
        if(this.password.length()<6)
            return false;
        if(this.email.length()<6)
            return false;
        return true;
    }
}
