package com.teaching.upbringing.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.utils.ScreenUtils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author bb
 * @time 2019/11/6 16:17
 * @des ${TODO}
 **/
public class CommonAddAdapter extends BaseQuickAdapter<CommonAddEntity, BaseViewHolder> {
    public CommonAddAdapter(@Nullable List<CommonAddEntity> data) {
        super(R.layout.item_common_addr, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonAddEntity item) {
        helper.setText(R.id.tv_add_title, item.getName())
                .setText(R.id.tv_add_detail, item.getName()+item.getHouseNumber())
        .addOnClickListener(R.id.tvDelete_edittask_item)
        .addOnClickListener(R.id.tv_update);
        TextView tvIsDefault = helper.getView(R.id.tv_is_defalut);
        tvIsDefault.setVisibility(item.isIfDefault()? View.VISIBLE:View.GONE);
        RelativeLayout rl_linearout = helper.getView(R.id.rl_linearout);
        rl_linearout.post(() -> {
            ViewGroup.LayoutParams layoutParams = rl_linearout.getLayoutParams();
            layoutParams.width =
                    ScreenUtils.getScreenWidth(rl_linearout.getContext());
            rl_linearout.setLayoutParams(layoutParams);
        });

    }

    public void removeData(int position){
        getData().remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
