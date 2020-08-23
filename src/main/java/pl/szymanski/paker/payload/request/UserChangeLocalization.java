package pl.szymanski.paker.payload.request;

public class UserChangeLocalization {
    String id;
    String newLocalization;

    public UserChangeLocalization() {
    }

    public UserChangeLocalization(String id, String newLocalization) {
        this.id = id;
        this.newLocalization = newLocalization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewLocalization() {
        return newLocalization;
    }

    public void setNewLocalization(String newLocalization) {
        this.newLocalization = newLocalization;
    }
}
