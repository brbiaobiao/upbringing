package com.teaching.upbringing.modular.mine;

import android.widget.TextView;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.SpannableHelper;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

import butterknife.BindView;

/**
 * @author ChenHh
 * @time 2019/10/28 0:15
 * @des
 **/
public class MyInfromationFragment extends BaseMVPFragment<MyInformationContract.Ipresenter> implements MyInformationContract.IView {

    @BindView(R.id.tv_post)
    TextView mTvPost;
    @BindView(R.id.tv_follow)
    TextView mTvFollow;
    @BindView(R.id.tv_fans)
    TextView mTvFans;
    @BindView(R.id.tv_credit)
    TextView mTvCredit;
    @BindView(R.id.tv_course)
    TextView mTvCourse;
    @BindView(R.id.tv_score)
    TextView mTvScore;

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_informantion;
    }

    @Override
    protected void init() {
        SpannableHelper.start("0\n").color(AppUtils.getColor(R.color.black)).spSize(AppUtils.getApp(), 16)
                .next("帖子").color(AppUtils.getColor(R.color.color_7a7a7a)).spSize(AppUtils.getApp(), 11).show2(mTvPost);
        SpannableHelper.start("0\n").color(AppUtils.getColor(R.color.black)).spSize(AppUtils.getApp(), 16)
                .next("关注").color(AppUtils.getColor(R.color.color_7a7a7a)).spSize(AppUtils.getApp(), 11).show2(mTvFollow);
        SpannableHelper.start("0\n").color(AppUtils.getColor(R.color.black)).spSize(AppUtils.getApp(), 16)
                .next("粉丝").color(AppUtils.getColor(R.color.color_7a7a7a)).spSize(AppUtils.getApp(), 11).show2(mTvFans);
        SpannableHelper.start("0\n").color(AppUtils.getColor(R.color.black)).spSize(AppUtils.getApp(), 16)
                .next("学分").color(AppUtils.getColor(R.color.color_7a7a7a)).spSize(AppUtils.getApp(), 11).show2(mTvCredit);
        SpannableHelper.start("0\n").color(AppUtils.getColor(R.color.black)).spSize(AppUtils.getApp(), 16)
                .next("学习课程").color(AppUtils.getColor(R.color.color_7a7a7a)).spSize(AppUtils.getApp(), 11).show2(mTvCourse);
        SpannableHelper.start("0\n").color(AppUtils.getColor(R.color.black)).spSize(AppUtils.getApp(), 16)
                .next("学员评分").color(AppUtils.getColor(R.color.color_7a7a7a)).spSize(AppUtils.getApp(), 11).show2(mTvScore);
    }

}
