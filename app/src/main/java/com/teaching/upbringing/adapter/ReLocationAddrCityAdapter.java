package com.teaching.upbringing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author ChenHh
 * @time 2019/11/10 5:00
 * @des
 **/
public class ReLocationAddrCityAdapter extends BaseQuickAdapter<ListAllRegionByNameEntity, BaseViewHolder> {
    public ReLocationAddrCityAdapter(@Nullable List<ListAllRegionByNameEntity> data) {
        super(R.layout.item_relocation_city, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListAllRegionByNameEntity item) {
        helper.setText(R.id.area_name, item.getName())
                .addOnClickListener(R.id.area_name);
    }
}
