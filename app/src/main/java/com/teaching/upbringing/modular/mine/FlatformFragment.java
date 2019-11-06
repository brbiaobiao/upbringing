package com.teaching.upbringing.modular.mine;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.teaching.upbringing.R;
import com.teaching.upbringing.adapter.FlatformAdapter;
import com.teaching.upbringing.modular.setting.AboutUsActivity;
import com.teaching.upbringing.modular.user.UpdatePwdActivity;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;
import com.teaching.upbringing.utils.ContractUtils;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> strings;
    private FlatformAdapter adapter;

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_flatforma;
    }

    @Override
    protected void init() {
        mRvFlatform.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRvFlatform.setHasFixedSize(true);
        mRvFlatform.setNestedScrollingEnabled(false);
        strings = new ArrayList<>();
        strings.add("认证");
        strings.add("身份切换");
        strings.add("教师招募");
        strings.add("建议反馈");
        strings.add("联系客服");
        strings.add("紧急报警");
        strings.add("关于我们");
        strings.add("检测更新");
        strings.add("修改密码");
        strings.add("商务合作");
        setAdapter(strings);

        initEvent();
    }

    @Override
    public void setAdapter(List<String> list) {
        if (adapter == null) {
            adapter = new FlatformAdapter(list);
            mRvFlatform.setAdapter(adapter);
        } else {
            adapter.setNewData(list);
        }
    }

    private void initEvent() {
        mRvFlatform.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String text = strings.get(position);
                switch (text) {
                    case "联系客服":
                        ContractUtils.UseContractDialog(getActivity(), "123456789", "12345678921");
                        break;
                    case "关于我们":
                        AboutUsActivity.goInto(getActivity());
                        break;
                    case "修改密码":
                        UpdatePwdActivity.goInto(getActivity());
                        break;
                }
            }
        });
    }
}
