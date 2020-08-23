package pl.szymanski.paker.payload.response;

public class CityResponse {
    private String id;
    private String name;
    private String province;
    private String provinceId;
    private String zipCode;

    public CityResponse(String id, String name, String privince, String zipCode) {
        this.id = id;
        this.name = name;
        this.province = privince;
        this.zipCode = zipCode;
    }

    public CityResponse() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
