package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.AllCityEntity;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;

import java.util.List;

/**
 * @author bb
 * @time 2019/10/29 10:33
 * @des ${TODO}
 **/
public interface LocationAddrContract {

    interface IView extends IContextView, IProgressAble{
        void setCityAdapter(List<ListAllRegionByNameEntity> list);
        void setAllCityAdapter(List<AllCityEntity> allCityEntities);
        void letterUpdate(int position);
        void setLetterText(String letterText);
    }

    interface IPresenter extends IBasePresenter<IView>{

        void getListAllRegion();
        void listAllRegionByNameLike(String key);
        void onLetterUpdate(String letter);
    }
}
