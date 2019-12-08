package com.teaching.upbringing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lefore.tutoring.R;
import com.teaching.upbringing.entity.PersonerFuncWrapper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * @author bb
 * @time 2019/10/28 15:13
 * @des ${TODO}
 **/
public class TeacherManagementAdapter extends BaseQuickAdapter<PersonerFuncWrapper, BaseViewHolder> {
    public TeacherManagementAdapter(@Nullable ArrayList<PersonerFuncWrapper> funcWrappers) {
        super(R.layout.item_used_service, funcWrappers);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonerFuncWrapper item) {
        helper.setText(R.id.tv_item, item.getFuncName())
                .setImageResource(R.id.iv_item, item.getFuncResId())
                .addOnClickListener(R.id.item_ll);

    }
}
