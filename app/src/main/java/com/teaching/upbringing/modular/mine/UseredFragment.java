package com.teaching.upbringing.modular.mine;

import com.teaching.upbringing.R;
import com.teaching.upbringing.adapter.UsedServerAdapter;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> strings;


    @Override
    protected Integer getContentId() {
        return R.layout.fragment_used;
    }

    @Override
    protected void init() {
        mRvService.setLayoutManager(new GridLayoutManager(getActivity(),4));
        mRvService.setHasFixedSize(true);
        mRvService.setNestedScrollingEnabled(false);
        strings = new ArrayList<>();
        strings.add("定制课程");
        strings.add("售后");
        strings.add("收藏");
        strings.add("钱包");
        strings.add("评价管理");
        setAdapter(strings);
    }

    @Override
    public void setAdapter(List<String> list) {
        if(adapter == null) {
            adapter = new UsedServerAdapter(list);
            mRvService.setAdapter(adapter);
        }else {
            adapter.setNewData(list);
        }
    }
}
