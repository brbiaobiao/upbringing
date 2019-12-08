package com.teaching.upbringing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;
import com.teaching.upbringing.entity.ClassListEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author bb
 * @time 2019/11/6 16:17
 * @des ${TODO}
 **/
public class ClassListAdapter extends BaseQuickAdapter<ClassListEntity, BaseViewHolder> {
    public ClassListAdapter(@Nullable List<ClassListEntity> data) {
        super(R.layout.item_class_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassListEntity item) {
        helper.setText(R.id.tv_class_name, item.getClass_name())
                .setText(R.id.tv_class_area, "上课区域：" + item.getClass_area())
                .setText(R.id.tv_shelf_life, "上架有效期：" + item.getShelf_life())
                .setText(R.id.tv_class_time,"总课时："+item.getTotal_class_num()+"小时"+"/ 学习人数："+item.getLeaners_num()+"人")
                .setText(R.id.tv_class_price,"单价："+item.getUnit_price()+"元"+"/ 总价："+item.getTotal_price()+"元");
        int class_type = item.getClass_type();
        switch (class_type) {
            case 1 :
                helper.setText(R.id.tv_class_type,"已上架")
                        .setTextColor(R.id.tv_class_type, AppUtils.getColor(R.color.color00AE66))
                .setText(R.id.tv_one_btn,"下架")
                .setText(R.id.tv_two_btn,"详情")
                .setVisible(R.id.tv_three_btn,false)
                .setVisible(R.id.tv_four_btn,false);
                break;
            case 2 :
                helper.setText(R.id.tv_class_type,"已下架")
                        .setTextColor(R.id.tv_class_type, AppUtils.getColor(R.color.color_BBBBBB))
                        .setText(R.id.tv_one_btn,"删除")
                        .setText(R.id.tv_two_btn,"切花区域")
                        .setVisible(R.id.tv_three_btn,true)
                        .setVisible(R.id.tv_four_btn,true);
                break;
            case 3 :
                helper.setText(R.id.tv_class_type,"已过期")
                        .setTextColor(R.id.tv_class_type, AppUtils.getColor(R.color.color_CD2A2A))
                        .setText(R.id.tv_one_btn,"删除")
                        .setText(R.id.tv_two_btn,"切花区域")
                        .setText(R.id.tv_three_btn,"详情")
                        .setVisible(R.id.tv_three_btn,true)
                        .setVisible(R.id.tv_four_btn,false);
                break;
            case 4 :
                helper.setText(R.id.tv_class_type,"待审核")
                        .setTextColor(R.id.tv_class_type, AppUtils.getColor(R.color.color_FD8440))
                        .setText(R.id.tv_one_btn,"删除")
                        .setText(R.id.tv_two_btn,"切花区域")
                        .setText(R.id.tv_three_btn,"审核")
                        .setVisible(R.id.tv_three_btn,true)
                        .setVisible(R.id.tv_four_btn,true);
                break;
            case 5 :
                helper.setText(R.id.tv_class_type,"审核中")
                        .setTextColor(R.id.tv_class_type, AppUtils.getColor(R.color.color_FD8440))
                        .setText(R.id.tv_one_btn,"取消审核")
                        .setText(R.id.tv_two_btn,"详情")
                        .setVisible(R.id.tv_three_btn,false)
                        .setVisible(R.id.tv_four_btn,false);
                break;
            case 6 :
                helper.setText(R.id.tv_class_type,"草稿")
                        .setTextColor(R.id.tv_class_type, AppUtils.getColor(R.color.color_0AA0FE))
                        .setText(R.id.tv_one_btn,"删除")
                        .setText(R.id.tv_two_btn,"切花区域")
                        .setText(R.id.tv_three_btn,"编辑")
                        .setText(R.id.tv_four_btn,"复制")
                        .setVisible(R.id.tv_three_btn,true)
                        .setVisible(R.id.tv_four_btn,true);
                break;
        }
    }

}
