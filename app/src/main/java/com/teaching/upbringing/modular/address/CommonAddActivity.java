package com.teaching.upbringing.modular.address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lefore.tutoring.R;
import com.outsourcing.library.utils.OnResultUtil;
import com.outsourcing.library.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teaching.upbringing.adapter.CommonAddAdapter;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.ToastUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author bb
 * @time 2019/11/6 15:24
 * @des ${常用地址}
 **/
public class CommonAddActivity extends BaseMVPActivity<CommonAddContract.IPresenter> implements CommonAddContract.IView {

    @BindView(R.id.rv_common_add)
    RecyclerView mRvCommonAdd;
    @BindView(R.id.smart_rel)
    SmartRefreshLayout mSmartRel;
    @BindView(R.id.tv_empty_view)
    TextView mTvEmptyView;
    private CommonAddAdapter addAdapter;

    public static Intent goCallInto(Context context) {
        Intent intent = new Intent(context, CommonAddActivity.class);
        return intent;
    }

    @Override
    protected CommonAddContract.IPresenter initPresenter() {
        return new CommonAddPresenter(this);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_common_add;
    }

    @Override
    protected void init() {
        setTitleText("常用地址");
        StatusBarUtil.setStatusBarColor(this,R.color.white);
        mRvCommonAdd.setLayoutManager(new LinearLayoutManager(this));
        mSmartRel.autoRefresh();
        mSmartRel.setOnRefreshListener(refreshLayout -> getPresenter().getAddressList());
        initEvent();
    }

    private void initEvent() {
        mRvCommonAdd.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tvDelete_edittask_item:
                        getPresenter().deleteAddress(position);
                        break;
                    case R.id.tv_update:
                        CommonAddEntity commonAddEntity = (CommonAddEntity) adapter.getData().get(position);
                        new OnResultUtil(CommonAddActivity.this).call(AddAddressActivity.getCallIntent(CommonAddActivity.this,
                                commonAddEntity))
                                .filter(OnResultUtil::isOk)
                                .subscribe(activityResultInfo -> getPresenter().getAddressList());
                        break;
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.add_address)
    public void onViewClicked() {
        new OnResultUtil(this).call(AddAddressActivity.getCallIntent(this,null))
                .filter(OnResultUtil::isOk)
                .subscribe(activityResultInfo -> getPresenter().getAddressList());
    }

    @Override
    public void setAdapter(List<CommonAddEntity> commonAddEntityList) {
        if (addAdapter == null) {
            addAdapter = new CommonAddAdapter(commonAddEntityList);
            mRvCommonAdd.setAdapter(addAdapter);
        } else {
            addAdapter.setNewData(commonAddEntityList);
        }
    }

    @Override
    public void removeData(int position) {
        ToastUtil.showShort("删除成功");
        addAdapter.removeData(position);
    }

    @Override
    public void refreshFinish() {
        mSmartRel.finishRefresh();
    }

    @Override
    public void setEmptyView(boolean flag) {
        mTvEmptyView.setVisibility(flag?View.VISIBLE:View.GONE);
    }

}
