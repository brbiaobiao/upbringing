package com.teaching.upbringing.modular.mine;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lefore.tutoring.R;
import com.teaching.upbringing.adapter.UsedServerAdapter;
import com.teaching.upbringing.entity.PersonerFuncWrapper;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author ChenHh
 * @time 2019/10/28 0:20
 * @des
 **/
public class UseredFragment extends BaseMVPFragment<UseredContract.Ipresenter> implements UseredContract.IView {

    @BindView(R.id.rv_service)
    RecyclerView mRvService;

    private UsedServerAdapter adapter;

    public static final String EXTRA_KEY_FUNC_LIST = "UseredFragment";
    private ArrayList<PersonerFuncWrapper> funcWrappers;


    public static UseredFragment newInstance(ArrayList<PersonerFuncWrapper> funcWrapperList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_KEY_FUNC_LIST, funcWrapperList);
        UseredFragment fragment = new UseredFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_used;
    }

    @Override
    protected void init() {
        funcWrappers = getArguments().getParcelableArrayList(EXTRA_KEY_FUNC_LIST);
        mRvService.setLayoutManager(new GridLayoutManager(getActivity(),4));
        mRvService.setHasFixedSize(true);
        mRvService.setNestedScrollingEnabled(false);
        setAdapter(funcWrappers);
        initEvent();
    }

    @Override
    public void setAdapter(ArrayList<PersonerFuncWrapper> funcWrappers) {
        if(adapter == null) {
            adapter = new UsedServerAdapter(funcWrappers);
            mRvService.setAdapter(adapter);
        }else {
            adapter.setNewData(funcWrappers);
        }
    }

    private void initEvent() {
        mRvService.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PersonerFuncWrapper funcWrapper = funcWrappers.get(position);
                String funcName = funcWrapper.getFuncName();
                switch (funcName) {
                    case "评价管理":
                        break;
                }
            }
        });
    }
}
