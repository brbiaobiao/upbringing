package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

import java.util.Map;

/**
 * @author bb
 * @time 2019/10/29 10:33
 * @des ${TODO}
 **/
public interface AddAddressContract {

    interface IView extends IContextView, IProgressAble{
        void afterAdd();
    }

    interface IPresenter extends IBasePresenter<IView>{
        void addAddress(Map<String,Object> map);
    }
}
