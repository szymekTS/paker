package pl.szymanski.paker.payload.response;

import pl.szymanski.paker.models.Status;

import java.util.List;

public class StatusList {
    List<Status> list;

    public StatusList() {
    }

    public StatusList(List<Status> list) {
        this.list = list;
    }

    public List<Status> getList() {
        return list;
    }

    public void setList(List<Status> list) {
        this.list = list;
    }
}
