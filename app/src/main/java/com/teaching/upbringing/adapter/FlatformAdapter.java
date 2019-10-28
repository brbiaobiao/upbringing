package com.teaching.upbringing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.teaching.upbringing.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author bb
 * @time 2019/10/28 15:13
 * @des ${TODO}
 **/
public class FlatformAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FlatformAdapter(@Nullable List<String> data) {
        super(R.layout.item_used_service, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item,item);
    }
}
