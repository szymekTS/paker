package pl.szymanski.paker.payload.request;

import pl.szymanski.paker.models.enums.EStatus;

public class OrderAddStatus {
    String orderId;
    String workerID;
    EStatus status;
    String comment;

    public OrderAddStatus() {
    }

    public OrderAddStatus(String orderId, String workerID, EStatus status, String comment) {
        this.orderId = orderId;
        this.workerID = workerID;
        this.status = status;
        this.comment = comment;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
