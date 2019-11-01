package com.teaching.upbringing.modular.mine;

import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;

/**
 * @author bb
 * @time 2019/11/1 16:24
 * @des ${TODO}
 **/
public class PersonInfoPersenter extends Presenter<PersonInforContract.IView> implements PersonInforContract.Ipresenter{

    private PersonInforModel personInforModel;

    public  PersonInfoPersenter(PersonInforContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }


    @Override
    public void initData() {

    }
}
