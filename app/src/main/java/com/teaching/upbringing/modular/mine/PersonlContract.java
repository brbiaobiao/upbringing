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
public interface PersonlContract {

    interface IView extends IContextView, IProgressAble{
        void setInfo(PersonInforEntity personInforEntity);
    }

    interface Ipresenter extends IBasePresenter<IView> {
        void initData();
    }
}
