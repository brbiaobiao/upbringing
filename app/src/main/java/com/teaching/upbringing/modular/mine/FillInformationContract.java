package com.teaching.upbringing.modular.mine;

import android.content.Intent;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface FillInformationContract {

    interface IView extends IContextView, IProgressAble{
        void upDataCallBack();
    }

    interface Ipresenter extends IBasePresenter<IView> {
        void getIntent(Intent intent);

        void upDataInfor(String info);
    }
}
