package com.teaching.upbringing.modular.teacher.classelse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;
import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @author ChenHh
 * @time 2019/12/8 12:49
 * @des
 **/
public class ClassListActivity extends BaseMVPActivity {
    @BindView(R.id.tablayout)
    SlidingTabLayout mTablayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    //1 2 3 4 5 6
    private String[] mTitles = {"已上架", "已下架", "已过期", "待审核", "审核中", "草稿"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    public static void gonInto(Context context){
        Intent intent = new Intent(context, ClassListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_class_list;
    }

    @Override
    protected void init() {
        setTitleText("课程列表");
        isShowTitleRightText(true);
        setTitleRightText("违规记录").setTitleRightTextColor(AppUtils.getColor(R.color.color_1A1A1A))
        .setTitleRightTextClick(view -> {

        });
        //添加Fragment
        addFragments();
        //new一个适配器
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        //设置ViewPager与适配器关联
        mViewPager.setAdapter(mAdapter);
        //设置Tab与ViewPager关联
        mTablayout.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTitles.length);
    }

    private void addFragments(){
        for(int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", i+1);
            ClassListFragment classListFragment = new ClassListFragment();
            classListFragment.setArguments(bundle);
            fragments.add(classListFragment);
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
