package pl.szymanski.paker.payload.request;

public class CityRequest {
    private String name;
    private String province;
    private String zipCode;

    public CityRequest() {
    }

    public CityRequest(String name, String province, String zipCode) {
        this.name = name;
        this.province = province;
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public boolean isValid(){
        return !this.name.isEmpty() && !this.province.isEmpty() && !this.zipCode.isEmpty();
    }
}
