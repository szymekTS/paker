package pl.szymanski.paker.payload.request;

import java.util.List;

public class CargoCheckType {
    List<CargoItem> list;

    public CargoCheckType() {
    }

    public CargoCheckType(List<CargoItem> list) {
        this.list = list;
    }

    public List<CargoItem> getList() {
        return list;
    }

    public void setList(List<CargoItem> list) {
        this.list = list;
    }
}

