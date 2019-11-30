package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.IdentityAuthStatusEntity;
import com.teaching.upbringing.entity.PersonerFuncWrapper;

import java.util.ArrayList;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface AuthenticateContract {

    interface IView extends IContextView, IProgressAble {
        void setIdentityAuthStatus(IdentityAuthStatusEntity identityAuthStatusEntity);
    }

    interface Ipresenter extends IBasePresenter<IView> {
        void getIdentityAuthStatus();
    }
}
