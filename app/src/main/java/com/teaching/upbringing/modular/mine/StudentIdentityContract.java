package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.IdentityAuthEntity;
import com.teaching.upbringing.entity.OssEntity;
import com.teaching.upbringing.entity.UserInfoEntity;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface StudentIdentityContract {

    interface IView extends IContextView, IProgressAble {
        void getApplyStudent(UserInfoEntity userInfoEntity);
        void setIdentityAuth(IdentityAuthEntity identityAuthEntity);
    }

    interface Ipresenter extends IBasePresenter<IView> {
        void setApplyStudent(String name,int sex);
        void gitIdentityAuth(int id);
    }
}
