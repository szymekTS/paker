package pl.szymanski.paker.payload.request;

import pl.szymanski.paker.models.Item;

import java.util.List;

public class CargoCheckType {
    List<Item> list;

    public CargoCheckType() {
    }

    public CargoCheckType(List<Item> list) {
        this.list = list;
    }

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
}

