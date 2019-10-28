package com.teaching.upbringing.mvpBase;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author bb
 * @time 2019/10/28 16:34
 * @des ${TODO}
 **/
public abstract class BaseListAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public BaseListAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }
}
