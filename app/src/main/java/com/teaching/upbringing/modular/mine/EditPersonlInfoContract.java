package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.OssEntity;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.entity.UserInfoEntity;

import java.io.File;

/**
 * @author ChenHh
 * @time 2019/10/27 16:11
 * @des
 **/
public interface EditPersonlInfoContract {

    interface IView extends IContextView, IProgressAble{
        void setInfor(PersonInforEntity personInforEntity);
        void getOss(OssEntity ossEntity);
        void getImgUrl(UserInfoEntity userInfoEntity);
    }

    interface Ipresenter extends IBasePresenter<IView> {
        void getInfo(boolean needLoading);
        void setSex(int sex);
        void saveUserImg(File file);
        void setOss(int code);
        void  setImgUrl(String ImgUrl);
    }
}
