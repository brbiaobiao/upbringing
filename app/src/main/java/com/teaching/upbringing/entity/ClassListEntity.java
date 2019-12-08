package com.teaching.upbringing.entity;

/**
 * @author ChenHh
 * @time 2019/12/8 15:03
 * @des
 **/
public class ClassListEntity {
    private String class_name;
    private String class_area;
    private String shelf_life;//上架有效期
    private int total_class_num;
    private int leaners_num;
    private float unit_price;
    private float total_price;
    private int class_type;//1：已上架 2：已下架 3:已过去 4：待审核 5：审核中 6：草稿

    public ClassListEntity(String class_name, String class_area, String shelf_life, int total_class_num, int leaners_num, float unit_price, float total_price, int class_type) {
        this.class_name = class_name;
        this.class_area = class_area;
        this.shelf_life = shelf_life;
        this.total_class_num = total_class_num;
        this.leaners_num = leaners_num;
        this.unit_price = unit_price;
        this.total_price = total_price;
        this.class_type = class_type;
    }

    public String getClass_name() {
        return class_name;
    }

    public ClassListEntity setClass_name(String class_name) {
        this.class_name = class_name;
        return this;
    }

    public String getClass_area() {
        return class_area;
    }

    public ClassListEntity setClass_area(String class_area) {
        this.class_area = class_area;
        return this;
    }

    public String getShelf_life() {
        return shelf_life;
    }

    public ClassListEntity setShelf_life(String shelf_life) {
        this.shelf_life = shelf_life;
        return this;
    }

    public int getTotal_class_num() {
        return total_class_num;
    }

    public ClassListEntity setTotal_class_num(int total_class_num) {
        this.total_class_num = total_class_num;
        return this;
    }

    public int getLeaners_num() {
        return leaners_num;
    }

    public ClassListEntity setLeaners_num(int leaners_num) {
        this.leaners_num = leaners_num;
        return this;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public ClassListEntity setUnit_price(float unit_price) {
        this.unit_price = unit_price;
        return this;
    }

    public float getTotal_price() {
        return total_price;
    }

    public ClassListEntity setTotal_price(float total_price) {
        this.total_price = total_price;
        return this;
    }

    public int getClass_type() {
        return class_type;
    }

    public ClassListEntity setClass_type(int class_type) {
        this.class_type = class_type;
        return this;
    }
}
