package com.teaching.upbringing.entity;

import java.io.Serializable;

/**
 * @author ChenHh
 * @time 2019/11/17 22:54
 * @des
 **/
public class CityHitstory implements Serializable {

    private String city_name;

    public String getCity_name() {
        return city_name;
    }

    public CityHitstory setCity_name(String city_name) {
        this.city_name = city_name;
        return this;
    }
}
