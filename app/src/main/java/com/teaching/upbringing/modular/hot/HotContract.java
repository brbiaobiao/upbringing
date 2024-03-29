package com.teaching.upbringing.modular.hot;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface HotContract {

    interface IView extends IContextView, IProgressAble{

    }

    interface Ipresenter extends IBasePresenter<IView> {

    }
}
