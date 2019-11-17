package com.teaching.upbringing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.CityHitstory;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author ChenHh
 * @time 2019/11/10 5:00
 * @des
 **/
public class CityHistotyAdapter extends BaseQuickAdapter<CityHitstory, BaseViewHolder> {
    public CityHistotyAdapter(@Nullable List<CityHitstory> data) {
        super(R.layout.item_city_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityHitstory item) {
        helper.setText(R.id.tv_city_history_name, item.getCity_name())
                .addOnClickListener(R.id.tv_city_history_name);
    }
}
