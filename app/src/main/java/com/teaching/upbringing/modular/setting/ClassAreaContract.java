package com.teaching.upbringing.modular.setting;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.ClassAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * @author bb
 * @time 2019/10/29 10:33
 * @des ${TODO}
 **/
public interface ClassAreaContract {

    interface IView extends IContextView, IProgressAble {
        void setAdapter(List<ClassAreaEntity> classAreaEntityList);

        void addSuccess();

        void removeAreaItem(int position);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void setAttendClassArea(Map<String, Object> map);

        void removeClassArea(int position);
    }
}
