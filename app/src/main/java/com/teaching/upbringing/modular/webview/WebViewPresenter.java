package com.teaching.upbringing.modular.webview;

import android.content.Intent;

import com.teaching.upbringing.presenter.Presenter;

/**
 * @author bb
 * @time 2019/10/25 11:23
 * @des ${TODO}
 **/
public class WebViewPresenter extends Presenter<WebViewContract.IView> implements WebViewContract.Ipresenter{

    private int type;
    private String url;

    public WebViewPresenter(WebViewContract.IView view) {
        super(view);
    }

    @Override
    public void loadPage(Intent intent) {
        type = intent.getIntExtra("webtype", -1);
        url = intent.getStringExtra("url");
        boolean isOPenHard = true;
        switch (type) {
            //举个例子
            case 0:
                getView().setTitle("Html5");
                getView().loadCommonUrl(url);
                getView().setTitleBarRightBtn(true, "分享", -1);
                break;
            case 1:
                getView().setTitle("Html5");
                getView().loadCommonUrl(url);
                break;
        }
        getView().setType(type);
        if (isOPenHard) {
            getView().openHardware();
        }
        javaScriptHandle();
    }

    public void javaScriptHandle() {
        //处理div class类型
        String[] divclass = "header&wid mtop10 mcenter&mtop10 mcenter clearfix&BAIDU_DUP_fp_wrapper".split("&");
        for (int i = 0; i < divclass.length; i++) {
            //            Log.e("------------>","divclass: "+divclass[i]);
            String classJs = "javascript:function HidenClass()" +
                    "{var x = document.getElementsByClassName('" + divclass[i] + "');" +
                    "var i;" +
                    "for(i = 0; i < x.length; i++){" +
                    "x[i].style.sidplay = 'none';" +
                    "}}";

            //            Log.e("------------>","classJs: "+classJs);

            getView().loadUrl(classJs, null);
            getView().loadUrl("javascript:HidenClass()", null);
        }

        // 处理div id 类型
        String[] divid = "wxmidad&trtop&toplogo&anewslist&botad&topfixed&wxmidad&header&foot&xiaohua&toplogo&classtitlr".split("&");
        for (int i = 0; i < divid.length; i++) {
            //            Log.e("------------>","divid["+i+"]： "+ divid[i]);
            String idJS = "javascript:function HidenID()" +
                    "{document.getElementById('" + divid[i] + "'),style.display='none';}";
            //处理特有逻辑
//            getView().loadUrl("","");
        }
    }
    

    @Override
    public void actionBarConfigPopupProcess() {

    }

    @Override
    public void onActionBarConfigClickProcess() {

    }

    @Override
    public void onTabSelectorProcess(int position) {

    }

    @Override
    public void onLoadFinish() {
        getView().setOk();
    }

    @Override
    public void applyShare() {
        //处理分享之后的逻辑，通知上一页后续操作等
    }

    @Override
    protected void init() {

    }

    @Override
    public void onViewInited() {

    }

    @Override
    public void onDestroyView() {

    }
}
