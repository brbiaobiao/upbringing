package com.teaching.upbringing.modular.teacher.classelse;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lefore.tutoring.R;
import com.teaching.upbringing.adapter.ClassListAdapter;
import com.teaching.upbringing.entity.ClassListEntity;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ChenHh
 * @time 2019/12/8 14:55
 * @des "已上架", "已下架", "已过期", "待审核", "审核中", "草稿"
 **/
public class ClassListFragment extends BaseMVPFragment<ClassListContract.Ipresenter> implements ClassListContract.IView {
    @BindView(R.id.tv_class_num)
    TextView mTvClassNum;
    @BindView(R.id.rv_class_list)
    RecyclerView mRvClassList;
    @BindView(R.id.tv_sort)
    TextView mTvSort;
    @BindView(R.id.tv_examine)
    TextView mTvExamine;
    @BindView(R.id.tv_add_class)
    TextView mTvAddClass;

    private ClassListAdapter classListAdapter;
    private List<ClassListEntity> classListEntityList = new ArrayList<>();
    private int id;

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_class_list;
    }

    @Override
    protected void init() {
        mRvClassList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getUserVisibleHint()) {
            id = getArguments().getInt("id", -1);
            initData();
            setAdapter(classListEntityList);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            id = getArguments().getInt("id", -1);
            initData();
            setAdapter(classListEntityList);
        }
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            ClassListEntity classListEntity = new ClassListEntity("高三物理成绩提高班", "广州/广州市/全市",
                    "2019.11.1至2020.6.1", 48, 6,
                    200, 4800, id);
            classListEntityList.add(classListEntity);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setAdapter(List<ClassListEntity> classListEntityList) {
        if (classListAdapter == null) {
            classListAdapter = new ClassListAdapter(classListEntityList);
            mRvClassList.setAdapter(classListAdapter);
        } else {
            classListAdapter.setNewData(classListEntityList);
        }

    }

    @OnClick({R.id.tv_sort, R.id.tv_examine, R.id.tv_add_class})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sort:
                break;
            case R.id.tv_examine:
                break;
            case R.id.tv_add_class:
                AddClassActivity.goInto(getActivity());
                break;
        }
    }
}
