package com.teaching.upbringing.modular.teacher.management;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lefore.tutoring.R;
import com.teaching.upbringing.adapter.TeacherManagementAdapter;
import com.teaching.upbringing.entity.PersonerFuncWrapper;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author ChenHh
 * @time 2019/12/8 1:20
 * @des
 **/
public class TeacherManagementFragment extends BaseMVPFragment<TeacherManagementContract.Ipresenter>
        implements TeacherManagementContract.IView {
    @BindView(R.id.rv_management)
    RecyclerView mRvManagement;
    private ArrayList<PersonerFuncWrapper> manageWrapperList = new ArrayList<>();

    private TeacherManagementAdapter adapter;

    @Override
    protected Integer getContentId() {
        return R.layout.fragement_teacher_management;
    }

    @Override
    protected void init() {
        mRvManagement.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        manageWrapperList = getManageWrapperList();
        if (adapter == null) {
            adapter = new TeacherManagementAdapter(manageWrapperList);
            mRvManagement.setAdapter(adapter);
        } else {
            adapter.setNewData(manageWrapperList);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private ArrayList<PersonerFuncWrapper> getManageWrapperList() {
        ArrayList<PersonerFuncWrapper> PersonerFuncWrapperList = new ArrayList<>();
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_landed_estate, "找课程"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_finance, "找教员"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_internet, "找机构"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_entertainment, "定制课"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_electronics, "团购课"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_travel, "悬赏问答"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_education, "热帖"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_teacher_more, "更多"));
        return PersonerFuncWrapperList;
    }

    private void initEvent() {
        mRvManagement.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PersonerFuncWrapper funcWrapper = manageWrapperList.get(position);
                String funcName = funcWrapper.getFuncName();
                switch (funcName) {
                    case "找课程":

                        break;
                    case "找教员":

                        break;
                    case "找机构":

                        break;
                    case "定制课":

                        break;
                    case "团购课":

                        break;
                    case "悬赏问答":

                        break;
                    case "热帖":

                        break;
                    case "更多":

                        break;
                }
            }
        });
    }
}
