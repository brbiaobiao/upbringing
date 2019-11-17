package com.teaching.upbringing.entity;

import com.outsourcing.library.net.RxHttpResponse;

/**
 * @author bb
 * @time 2019/11/1 16:38
 * @des ${TODO}
 **/
public class CaptchaEntity extends RxHttpResponse {

    private  String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
