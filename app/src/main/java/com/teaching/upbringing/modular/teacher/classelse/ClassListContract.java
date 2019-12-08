package com.teaching.upbringing.modular.teacher.classelse;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.ClassListEntity;

import java.util.List;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface ClassListContract {

    interface IView extends IContextView, IProgressAble{
        void setAdapter(List<ClassListEntity> classListEntityList);
    }

    interface Ipresenter extends IBasePresenter<IView> {
    }
}
