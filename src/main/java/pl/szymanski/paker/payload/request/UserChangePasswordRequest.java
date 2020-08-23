package pl.szymanski.paker.payload.request;

public class UserChangePasswordRequest {
    String id;
    String oldPassword;
    String password;

    public UserChangePasswordRequest() {
    }

    public UserChangePasswordRequest(String id, String oldPassword, String password) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public boolean isValid(){
        System.out.println(this.id);
        System.out.println(this.oldPassword);
        System.out.println(this.password);
        return true;//this.password.length()>=6 && this.oldPassword.length()>=6;
    }
}
