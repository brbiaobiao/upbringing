package com.teaching.upbringing.modular.setting;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.ClassAreaEntity;
import com.teaching.upbringing.model.ClassRegionModel;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;
import com.teaching.upbringing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author ChenHh
 * @time 2019/11/26 0:23
 * @des
 **/
public class ClassAreaPresenter extends Presenter<ClassAreaContract.IView> implements ClassAreaContract.IPresenter {

    private PersonInforModel personInforModel;
    private ClassRegionModel classRegionModel;
    private List<ClassAreaEntity> classAreaEntityList = new ArrayList<>();
    public ClassAreaPresenter(ClassAreaContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
        classRegionModel = new ClassRegionModel();
    }

    @Override
    public void getClassList() {
        getView().showProgress();
        classRegionModel.getClassList()
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NextObserver<List<ClassAreaEntity>>() {
                    @Override
                    public void onNext(List<ClassAreaEntity> classAreaEntities) {
                        getView().hideProgress();
                        for(int i = 0; i < classAreaEntities.size(); i++) {
                            classAreaEntities.get(i).setArea_title("上课区域"+(i+1));
                        }
                        classAreaEntityList = classAreaEntities;
                        getView().setAdapter(classAreaEntityList);
                        getView().finishRelsh();
                    }
                });
    }

    @Override
    public void setAttendClassArea(Map<String, Object> map) {
        getView().showProgress();
        classRegionModel.createClassArea(map)
                .compose(bindLife())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<ClassAreaEntity>() {
                    @Override
                    public void onNext(ClassAreaEntity classAreaEntity) {
                        getView().hideProgress();
                        getView().addSuccess();
                    }
                });
    }

    @Override
    public void removeClassArea(int position) {
        getView().showProgress();
        Map<String,String> map = new HashMap<>();
        map.put("id",classAreaEntityList.get(position).getId());
        classRegionModel.deleteClassArea(map)
                .compose(bindLife())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<ClassAreaEntity>() {
                    @Override
                    public void onNext(ClassAreaEntity classAreaEntity) {
                        getView().hideProgress();
                        getView().removeAreaItem(position);
                        ToastUtil.showSucceed("删除成功");
                    }
                });
    }
}
