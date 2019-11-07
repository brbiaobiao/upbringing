package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

/**
 * @author bb
 * @time 2019/10/29 10:33
 * @des ${TODO}
 **/
public interface SelectAddressContract {

    interface IView extends IContextView, IProgressAble{
    }

    interface IPresenter extends IBasePresenter<IView>{
    }
}
