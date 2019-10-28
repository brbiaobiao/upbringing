package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

import java.util.List;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface UseredContract {

    interface IView extends IContextView{
        void setAdapter(List<String> list);
    }

    interface Ipresenter extends IBasePresenter<IView> {

    }
}
