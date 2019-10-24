package com.teaching.upbringing.contact;

import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.TestEntity;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public interface MainContact {

    interface IView extends IContextView {
        void setData(TestEntity entity);
    }

    interface IPersenter extends IBasePresenter<IView> {
        void getTestData();
    }
}
