package com.teaching.upbringing.entity;

/**
 * @author bb
 * @time 2019/11/6 16:20
 * @des ${TODO}
 **/
public class CommonAddEntity {

    private String houseNumber;
    private int id;
    private boolean ifDefault;
    private String location;
    private String name;

    public CommonAddEntity(String houseNumber, boolean ifDefault, String name) {
        this.houseNumber = houseNumber;
        this.ifDefault = ifDefault;
        this.name = name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public CommonAddEntity setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public int getId() {
        return id;
    }

    public CommonAddEntity setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isIfDefault() {
        return ifDefault;
    }

    public CommonAddEntity setIfDefault(boolean ifDefault) {
        this.ifDefault = ifDefault;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public CommonAddEntity setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getName() {
        return name;
    }

    public CommonAddEntity setName(String name) {
        this.name = name;
        return this;
    }
}
