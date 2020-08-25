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
        this.email = email;
    }

    public boolean isValid() {
        System.out.println(this.name);
        System.out.println(this.surname);
        System.out.println(this.email);

        return this.email.length() >= 6;
    }
}
