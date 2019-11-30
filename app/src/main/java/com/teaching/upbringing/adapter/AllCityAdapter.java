package com.teaching.upbringing.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lefore.tutoring.R;
import com.teaching.upbringing.entity.AllCityEntity;

import java.util.List;


/**
 * Created by Administrator on 2017/1/12.
 */
public class AllCityAdapter extends BaseQuickAdapter<AllCityEntity, BaseViewHolder> {

    public AllCityAdapter(List<AllCityEntity> data) {
        super(R.layout.item_all_city, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, AllCityEntity item) {
        helper.setText(R.id.text_tv, item.getName())
        .addOnClickListener(R.id.text_tv);

        int position = helper.getLayoutPosition();
        String currentChar = item.getChar_new();
        String letter = null;
        if (position == 0) {
            letter = currentChar;
        } else {
            String char_new = getData().get(position - 1).getChar_new();
            if (!TextUtils.equals(char_new, currentChar)) {
                letter = currentChar;
            }
        }
        helper.setText(R.id.title_tv, letter);
        boolean flag = letter != null;
        View tvView = helper.getView(R.id.title_tv);
        if (flag) {
            tvView.setVisibility(View.VISIBLE);
        } else {
            tvView.setVisibility(View.GONE);
        }
    }
}
