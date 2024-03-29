package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.PersonInforEntity;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface PersonInforContract {

    interface IView extends IContextView, IProgressAble {
        void setInit(PersonInforEntity personInforEntity);
    }

    interface Ipresenter extends IBasePresenter<IView> {
        void initData(boolean needLoading);
    }
}
