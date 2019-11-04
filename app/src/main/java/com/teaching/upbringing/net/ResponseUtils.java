package com.teaching.upbringing.net;

import com.outsourcing.library.utils.AppUtils;
import com.teaching.upbringing.utils.Navigation;

/**
 * @author bb
 * @time 2019/11/4 11:19
 * @des ${}
 **/
public class ResponseUtils {

    static void toLogin(){
        Navigation.getInstance(AppUtils.getApp()).toLogin();
    }
}
