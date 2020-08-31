package pl.szymanski.paker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.format.annotation.DateTimeFormat;
import pl.szymanski.paker.models.enums.EStatus;

import java.util.Date;

@Document(collection = "statuses")
public class Status {

    @Id
    private String id;

    private EStatus statusCode;

    @DBRef
    private User worker;

    private String comments;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    public Status() {
        date = new Date();
    }

    public Status(EStatus statusCode, User worker, String comments) {
        this();
        this.statusCode = statusCode;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
