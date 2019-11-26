package com.teaching.upbringing.entity;

/**
 * @author bb
 * @time 2019/11/26 11:39
 * @des ${TODO}
 **/
public class ClassAreaEntity {

    private String area_title;
    private String area_name;

    public ClassAreaEntity(String area_title, String area_name) {
        this.area_title = area_title;
        this.area_name = area_name;
    }

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
}
