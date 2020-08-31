package pl.szymanski.paker.payload.request;

public class CarUpdate {
    private String id;
    private String localization;
    private boolean repairing;
    private boolean free;

    public CarUpdate() {
    }

    public CarUpdate(String id, String localization, boolean isRepair, boolean isFree) {
        this.id = id;
        this.localization = localization;
        this.repairing = isRepair;
        this.free = isFree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public boolean isRepairing() {
        return repairing;
    }

    public void setRepairing(boolean repairing) {
        this.repairing = repairing;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
