package com.teaching.upbringing.entity;

/**
 * @author bb
 * @time 2019/11/18 15:18
 * @des ${TODO}
 **/
public class ListSubRegionEntity {
    private String adCode;
    private int districtLevel;
    private String id;
    private String name;

    public String getAdCode() {
        return adCode;
    }

    public ListSubRegionEntity setAdCode(String adCode) {
        this.adCode = adCode;
        return this;
    }

    public int getDistrictLevel() {
        return districtLevel;
    }

    public ListSubRegionEntity setDistrictLevel(int districtLevel) {
        this.districtLevel = districtLevel;
        return this;
    }

    public String getId() {
        return id;
    }

    public ListSubRegionEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ListSubRegionEntity setName(String name) {
        this.name = name;
        return this;
    }
}
