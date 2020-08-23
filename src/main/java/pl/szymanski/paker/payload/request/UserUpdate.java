package pl.szymanski.paker.payload.request;

import java.util.ArrayList;
import java.util.List;

public class UserUpdate {
    private String userName;
    private String name;
    private String surname;
    private String number;
    private String email;
    private List<String> roles = new ArrayList<>();
    private String localization;

    public UserUpdate() {
    }

    public UserUpdate(String userName, String name, String surname, String number, String email, List<String> roles, String localization) {
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.email = email;
        this.roles = roles;
        this.localization = localization;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public boolean isValid(){
        if (this.userName.length() < 3)
            return false;
        if (this.email.length() < 3)
            return false;
        return true;
    }
}
