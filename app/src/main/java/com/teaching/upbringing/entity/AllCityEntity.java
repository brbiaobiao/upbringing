package com.teaching.upbringing.entity;

/**
 * @author ChenHh
 * @time 2019/11/10 15:28
 * @des
 **/
public class AllCityEntity {

    private String adCode;
    private int districtLevel;
    private String id;
    private String name;
    private String char_new;

    public String getAdCode() {
        return adCode;
    }

    public AllCityEntity setAdCode(String adCode) {
        this.adCode = adCode;
        return this;
    }

    public int getDistrictLevel() {
        return districtLevel;
    }

    public AllCityEntity setDistrictLevel(int districtLevel) {
        this.districtLevel = districtLevel;
        return this;
    }

    public String getId() {
        return id;
    }

    public AllCityEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AllCityEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getChar_new() {
        return char_new;
    }

    public AllCityEntity setChar_new(String char_new) {
        this.char_new = char_new;
        return this;
    }
}
