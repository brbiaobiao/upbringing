package com.teaching.upbringing.modular.address;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.outsourcing.library.manager.LoadMoreManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teaching.upbringing.R;
import com.teaching.upbringing.adapter.CommonAddAdapter;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author bb
 * @time 2019/11/6 15:24
 * @des ${TODO}
 **/
public class CommonAddActivity extends BaseMVPActivity<CommonAddContract.IPresenter> implements CommonAddContract.IView {

    @BindView(R.id.rv_common_add)
    RecyclerView mRvCommonAdd;
    @BindView(R.id.add_address)
    TextView mAddAddress;
    @BindView(R.id.smart_rel)
    SmartRefreshLayout mSmartRel;
    private LoadMoreManager loadMoreManager;
    private CommonAddAdapter addAdapter;
    private List<CommonAddEntity> commonAddEntityList = new ArrayList<>();

    public static Intent goCallInto(Context context) {
        Intent intent = new Intent(context, CommonAddActivity.class);
        return intent;
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_common_add;
    }

    @Override
    protected void init() {
        setTitleText("常用地址");
        /*loadMoreManager = new LoadMoreManager(mSmartRel,null,null);
        loadMoreManager.setOnKeyLoadMoreLinetener((key, pageSize, index) -> {
            // TODO: 2019/11/6 请求数据
        });
        loadMoreManager.setReversal(false);
        loadMoreManager.autoRefresh();*/
        mSmartRel.autoRefresh();
        mRvCommonAdd.setLayoutManager(new LinearLayoutManager(this));

        commonAddEntityList.add(new CommonAddEntity("广州第一人民医院", "就在海的那边山的那边啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦", true));
        commonAddEntityList.add(new CommonAddEntity("沙美公园", "就是那个公园对对对对对对对对对对对对对都丢地对对对", false));
        commonAddEntityList.add(new CommonAddEntity("hhhahha", "hhhahhaha", false));

        setAdapter(commonAddEntityList);
        initEvent();

    }

    private void initEvent() {
        mRvCommonAdd.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tvDelete_edittask_item:
                        addAdapter.removeData(position);
                        break;
                    case R.id.tv_update:

                        break;
                }
            }
        });
    }

    @OnClick(R.id.add_address)
    public void onViewClicked() {
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
}
