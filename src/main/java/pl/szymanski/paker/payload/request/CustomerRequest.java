package pl.szymanski.paker.payload.request;

public class CustomerRequest {
    private String name;
    private String surname;
    private String email;

    public CustomerRequest() {
    }

    public CustomerRequest(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

    }

    public boolean isValid() {
        return !this.name.isEmpty() && !this.surname.isEmpty() && !this.email.isEmpty();
    }
}
