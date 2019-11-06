package com.teaching.upbringing.entity;

/**
 * @author bb
 * @time 2019/11/6 16:20
 * @des ${TODO}
 **/
public class CommonAddEntity {

    public CommonAddEntity(String addr_title, String addr_detail, boolean is_default) {
        this.addr_title = addr_title;
        this.addr_detail = addr_detail;
        this.is_default = is_default;
    }

    private String addr_title;
    private String addr_detail;
    private boolean is_default;

    public String getAddr_title() {
        return addr_title;
    }

    public CommonAddEntity setAddr_title(String addr_title) {
        this.addr_title = addr_title;
        return this;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public CommonAddEntity setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
        return this;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public CommonAddEntity setIs_default(boolean is_default) {
        this.is_default = is_default;
        return this;
    }
}
