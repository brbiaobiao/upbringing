package com.teaching.upbringing.modular.setting;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CaptchaEntity;

/**
 * @author bb
 * @time 2019/10/29 10:33
 * @des ${TODO}
 **/
public interface SettingContract {

    interface IView extends IContextView, IProgressAble{
        void loginOut(CaptchaEntity entity);
    }

    interface IPresenter extends IBasePresenter<IView>{
        abstract void loginOut();
    }
}
