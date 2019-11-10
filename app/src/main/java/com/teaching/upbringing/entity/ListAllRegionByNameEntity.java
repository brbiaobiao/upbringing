package com.teaching.upbringing.entity;

/**
 * @author ChenHh
 * @time 2019/11/10 4:51
 * @des
 **/
public class ListAllRegionByNameEntity {

    private String adCode;
    private String districtLevel;
    private String id;
    private String name;

    public String getAdCode() {
        return adCode;
    }

    public ListAllRegionByNameEntity setAdCode(String adCode) {
        this.adCode = adCode;
        return this;
    }

    public String getDistrictLevel() {
        return districtLevel;
    }

    public ListAllRegionByNameEntity setDistrictLevel(String districtLevel) {
        this.districtLevel = districtLevel;
        return this;
    }

    public String getId() {
        return id;
    }

    public ListAllRegionByNameEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ListAllRegionByNameEntity setName(String name) {
        this.name = name;
        return this;
    }
}
