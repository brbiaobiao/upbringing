package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface MyInformationContract {

    interface IView extends IContextView{

    }

    interface Ipresenter extends IBasePresenter<IView> {

    }
}
