package com.teaching.upbringing.entity;

/**
 * @author bb
 * @time 2019/11/26 11:39
 * @des ${TODO}
 **/
public class ClassAreaEntity {

    private String adCode;
    private float areaRegionId;
    private String city;
    private float cityRegionId;
    private int districtLevel;
    private String id;
    private String province;
    private float provinceRegionId;
    private String region;


    private String area_title;
    private String area_name;

    public String getArea_title() {
        return area_title;
    }

    public ClassAreaEntity setArea_title(String area_title) {
        this.area_title = area_title;
        return this;
    }

    public String getArea_name() {
        return area_name;
    }

    public ClassAreaEntity setArea_name(String area_name) {
        this.area_name = area_name;
        return this;
    }

    public String getAdCode() {
        return adCode;
    }

    public ClassAreaEntity setAdCode(String adCode) {
        this.adCode = adCode;
        return this;
    }

    public float getAreaRegionId() {
        return areaRegionId;
    }

    public ClassAreaEntity setAreaRegionId(float areaRegionId) {
        this.areaRegionId = areaRegionId;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ClassAreaEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public float getCityRegionId() {
        return cityRegionId;
    }

    public ClassAreaEntity setCityRegionId(float cityRegionId) {
        this.cityRegionId = cityRegionId;
        return this;
    }

    public int getDistrictLevel() {
        return districtLevel;
    }

    public ClassAreaEntity setDistrictLevel(int districtLevel) {
        this.districtLevel = districtLevel;
        return this;
    }

    public String getId() {
        return id;
    }

    public ClassAreaEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public ClassAreaEntity setProvince(String province) {
        this.province = province;
        return this;
    }

    public float getProvinceRegionId() {
        return provinceRegionId;
    }

    public ClassAreaEntity setProvinceRegionId(float provinceRegionId) {
        this.provinceRegionId = provinceRegionId;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public ClassAreaEntity setRegion(String region) {
        this.region = region;
        return this;
    }
}
