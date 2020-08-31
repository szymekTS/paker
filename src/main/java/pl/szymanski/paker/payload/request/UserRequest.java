package pl.szymanski.paker.payload.request;

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
    private String localization;
    private Boolean isFree = true;
    private Boolean isDriver;

    public UserRequest() {
    }

    public UserRequest(String userName, String name, String surname, String number, String email, String password, Set<String> roles, String localization, Boolean isFree, Boolean isDriver) {
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.localization = localization;
        this.isFree = isFree;
        this.isDriver = isDriver;
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

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean free) {
        isFree = free;
    }

    public Boolean getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(Boolean driver) {
        isDriver = driver;
    }

    public boolean isValid() {
        if (this.userName.length() < 3)
            return false;
        if (this.password.length() < 6)
            return false;
        if (this.email.length() < 6)
            return false;
        return true;
    }
}
