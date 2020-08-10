package pl.szymanski.paker.payload.response;

import java.util.Set;

public class UserResponse {
    private String id;
    private String userName;
    private String name;
    private String surname;
    private String number;
    private String email;
    private Set<String> roles;
    private String localization;
    private Boolean isFree;

    public UserResponse(String id, String username, String name, String surname, String number, String email, String localization, boolean free) {
    }

    public UserResponse(String id, String userName, String name, String surname, String number, String email, Set<String> roles, String localization, Boolean isFree) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.email = email;
        this.roles = roles;
        this.localization = localization;
        this.isFree = isFree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
