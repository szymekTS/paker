package pl.szymanski.paker.payload.request;

public class StatusRequest {
    private String id;
    private String workerId;
    private String comment;

    public StatusRequest() {
    }

    public StatusRequest(String id, String workerId, String comment) {
        this.id = id;
        this.workerId = workerId;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public boolean isValid(){
        return !this.id.isEmpty() && !this.workerId.isEmpty();
    }
}
