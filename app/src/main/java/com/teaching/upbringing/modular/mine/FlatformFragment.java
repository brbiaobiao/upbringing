package com.teaching.upbringing.modular.mine;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lefore.tutoring.R;
import com.teaching.upbringing.adapter.FlatformAdapter;
import com.teaching.upbringing.entity.PersonerFuncWrapper;
import com.teaching.upbringing.modular.setting.AboutUsActivity;
import com.teaching.upbringing.modular.user.UpdatePwdActivity;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;
import com.teaching.upbringing.utils.ContractUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author ChenHh
 * @time 2019/10/28 0:22
 * @des
 **/
public class FlatformFragment extends BaseMVPFragment<FlatformContract.Ipresenter> implements FlatformContract.IView {

    @BindView(R.id.rv_flatform)
    RecyclerView mRvFlatform;

    private FlatformAdapter adapter;

    public static final String EXTRA_KEY_FUNC_LIST = "FlatformFragment";
    private ArrayList<PersonerFuncWrapper> funcWrappers;

    public static FlatformFragment newInstance(ArrayList<PersonerFuncWrapper> funcWrapperList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_KEY_FUNC_LIST, funcWrapperList);
        FlatformFragment fragment = new FlatformFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_flatforma;
    }

    @Override
    protected void init() {
        funcWrappers = getArguments().getParcelableArrayList(EXTRA_KEY_FUNC_LIST);
        mRvFlatform.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRvFlatform.setHasFixedSize(true);
        mRvFlatform.setNestedScrollingEnabled(false);
        setAdapter(funcWrappers);
        initEvent();
    }

    @Override
    public void setAdapter(ArrayList<PersonerFuncWrapper> funcWrappers) {
        if (adapter == null) {
            adapter = new FlatformAdapter(funcWrappers);
            mRvFlatform.setAdapter(adapter);
        } else {
            adapter.setNewData(funcWrappers);
        }
    }

    private void initEvent() {
        mRvFlatform.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PersonerFuncWrapper funcWrapper = funcWrappers.get(position);
                String funcName = funcWrapper.getFuncName();
                switch (funcName) {
                    case "联系客服":
                        ContractUtils.UseContractDialog(getActivity(), "123456789", "12345678921");
                        break;
                    case "关于我们":
                        AboutUsActivity.goInto(getActivity());
                        break;
                    case "修改密码":
                        UpdatePwdActivity.goInto(getActivity());
                        break;
                    case "身份切换":
                        IdentityConversionActivity.goInto(getActivity());
                        break;
                    case "认证":
                        AuthenticateActivity.goInto(getActivity());
                        break;
                }
            }
        });
    }
}
