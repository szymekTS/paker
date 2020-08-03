package pl.szymanski.paker.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import pl.szymanski.paker.models.enums.EStatus;

@Document(collection = "statuses")
public class Status {

    @Id
    private String id;

    private EStatus statusCode;
    @DBRef
    private City location;
    @DBRef
    private User worker;

    private String comments;

    public Status() {
    }

    public Status(EStatus statusCode, City location, User worker, String comments) {
        this.statusCode = statusCode;
        this.location = location;
        this.worker = worker;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(EStatus statusCode) {
        this.statusCode = statusCode;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
