package com.teaching.upbringing.modular.webview;

import android.accounts.Account;
import android.content.Intent;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

/**
 * @author bb
 * @time 2019/10/25 10:47
 * @des ${TODO}
 **/
public interface WebViewContract {

    interface IView extends IContextView,IProgressAble{
        void initVisiable();

        void setTitle(String title);

        void setActionBarRight(String txt, int iconRes, int visible);

        void loadUrl(String url, Account account);

        void showErrorPage();

        void loadCommonUrl(String url);

        void setZoomEnable(boolean isEnable);

        void setTitleBarRightBtn(boolean isShow, String text, int imgId);

        void showPupowindow();

        void initTabLayout(String... title);

        void initActionBarTitle(int type);

        //        void addInterface(Object obj, String name);


        void saveEdtReportForWeb();

        void setTitleByLable(boolean isByNet);

        void setOk();

        void setType(int type);

        void showBtns();

        /**
         * 开启硬件加速
         */
        void openHardware();
    }

    interface Ipresenter extends IBasePresenter<IView>{
        abstract void loadPage(Intent intent);// 根据type显示页面

        abstract void actionBarConfigPopupProcess();

        abstract void onActionBarConfigClickProcess();

        abstract void onTabSelectorProcess(int position);

        abstract void onLoadFinish();

        /**
         * 发送分享成功
         */
        abstract void applyShare();
    }
}
