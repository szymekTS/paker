package pl.szymanski.paker.payload.response;

public class StatusResponse {
    private String id;
    private String statusCode;
    private String worker;
    private String comments;

    public StatusResponse() {
    }

    public StatusResponse(String id, String statusCode, String worker, String comments) {
        this.id = id;
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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
