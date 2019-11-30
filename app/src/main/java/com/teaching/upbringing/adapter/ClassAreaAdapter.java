package com.teaching.upbringing.adapter;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lefore.tutoring.R;
import com.teaching.upbringing.entity.ClassAreaEntity;
import com.teaching.upbringing.utils.ScreenUtils;
import com.teaching.upbringing.widget.SlidingButtonView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author bb
 * @time 2019/11/6 16:17
 * @des ${TODO}
 **/
public class ClassAreaAdapter extends BaseQuickAdapter<ClassAreaEntity, BaseViewHolder> {
    public ClassAreaAdapter(@Nullable List<ClassAreaEntity> data) {
        super(R.layout.item_class_area, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassAreaEntity item) {
        helper.setText(R.id.tv_area_title, item.getArea_title())
                .setText(R.id.tv_area_name, item.getArea_name())
        .addOnClickListener(R.id.tvDelete_edittask_item);
        ConstraintLayout rl_linearout = helper.getView(R.id.cl_linearout);
        rl_linearout.post(() -> {
            ViewGroup.LayoutParams layoutParams = rl_linearout.getLayoutParams();
            layoutParams.width =
                    ScreenUtils.getScreenWidth(rl_linearout.getContext());
            rl_linearout.setLayoutParams(layoutParams);
        });
    }

    public void removeItem(int position){
        getData().remove(position);
//        resetDeleteBtn();
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    private void resetDeleteBtn() {
        for (int i = 0; i < getData().size(); i++) {
            SlidingButtonView buttonView = (SlidingButtonView) getViewByPosition(i, R.id.rootView);
            if (buttonView != null) {
                buttonView.hideDelete();
            }
        }
    }
}
