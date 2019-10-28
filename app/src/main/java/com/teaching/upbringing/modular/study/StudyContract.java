package com.teaching.upbringing.modular.study;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

/**
 * @author ChenHh
 * @time 2019/10/27 16:15
 * @des
 **/
public interface StudyContract {

    interface IView extends IContextView, IProgressAble{

    }

    interface IPresenter extends IBasePresenter<IView>{

    }
}
