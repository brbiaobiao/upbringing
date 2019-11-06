package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CommonAddEntity;

import java.util.List;

/**
 * @author bb
 * @time 2019/10/29 10:33
 * @des ${TODO}
 **/
public interface CommonAddContract {

    interface IView extends IContextView, IProgressAble{
        void setAdapter(List<CommonAddEntity> commonAddEntityList);
    }

    interface IPresenter extends IBasePresenter<IView>{
    }
}
