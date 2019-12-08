package com.teaching.upbringing.modular.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lefore.tutoring.R;
import com.teaching.upbringing.modular.community.CommunityFragment;
import com.teaching.upbringing.modular.hot.HotFragment;
import com.teaching.upbringing.modular.mine.PersonlFragment;
import com.teaching.upbringing.modular.teacher.management.TeacherManagementFragment;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ChenHh
 * @time 2019/12/8 0:54
 * @des
 **/
public class TeacherManagerActivity extends BaseMVPActivity {

    /**ORDER
     * 页面页码
     */
    public static final int PAGE_ORDER = 0;
    public static final int PAGE_MANAGEMENT = 1;
    public static final int PAGE_COMMUNITY = 2;
    public static final int PAGE_WORKENCH = 3;
    public static final int PAGE_HOT = 4;
    public static final int PAGE_MINE = 5;

    //当前显示的fragment
    private static final String CURRENT_FRAGMENT_ONLY = "CURRENT_FRAGMENT_SHOW";
    @BindView(R.id.fl_content_teacher)
    FrameLayout mFlContentTeacher;
    @BindView(R.id.order)
    RadioButton mOrder;
    @BindView(R.id.manage)
    RadioButton mManage;
    @BindView(R.id.community)
    RadioButton mCommunity;
    @BindView(R.id.workbench)
    RadioButton mWorkbench;
    @BindView(R.id.hotList)
    RadioButton mHotList;
    @BindView(R.id.mine)
    RadioButton mMine;
    @BindView(R.id.main_btngroup)
    RadioGroup mMainBtngroup;

    private ArrayList<Fragment> fragments;
    private Fragment mCurrentFragment;
    private int mcurrentPage = 0;

    private TeacherOrderFragment orderFragment;//订单
    private TeacherManagementFragment managementFragment;//管理
    private CommunityFragment communityFragment;//社区
    private TeacherWorkenchFragment workenchFragment;//工作台
    private HotFragment hotFragmentl;//热榜
    private PersonlFragment personlFragment;//我的

    private int mCurrentPageIndex = -1;

    public static void goInto(Context context){
        Intent intent = new Intent(context, TeacherManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_teacher_manager;
    }

    @Override
    protected void init() {
        hideHeadBar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            mcurrentPage = savedInstanceState.getInt(CURRENT_FRAGMENT_ONLY,0);

            //注意，添加顺序要跟下面添加的顺序一样！！！！
            if(fragments == null) {
                fragments = new ArrayList<>();
            }
            fragments.removeAll(fragments);
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_ORDER+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_MANAGEMENT+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_COMMUNITY+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_WORKENCH+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_HOT+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_MINE+""));

            //恢复fragment页面
            restoreFragment();


        }else{      //正常启动时调用

            //初始化fragments
            fragments = new ArrayList<>();
            orderFragment = new TeacherOrderFragment();
            fragments.add(orderFragment);
            managementFragment = new TeacherManagementFragment();
            fragments.add(managementFragment);
            communityFragment = new CommunityFragment();
            fragments.add(communityFragment);
            workenchFragment = new TeacherWorkenchFragment();
            fragments.add(workenchFragment);
            hotFragmentl = new HotFragment();
            fragments.add(hotFragmentl);
            personlFragment = new PersonlFragment();
            fragments.add(personlFragment);

            switchFragment(PAGE_MANAGEMENT);
            mMainBtngroup.check(R.id.workbench);
        }
    }

    public void switchFragment(int pageIndex) {
        if (fragments == null || pageIndex == mCurrentPageIndex) return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_alpha, R.anim.exit_aplha);
        Fragment fragment = fragments.get(pageIndex);
        if(!fragment.isAdded()) {
            if(mCurrentFragment == null) {
                ft.add(R.id.fl_content_teacher, fragment, mcurrentPage+"");
            }else {
                ft.hide(mCurrentFragment).add(R.id.fl_content_teacher, fragment, mcurrentPage+"");
            }
        }else {
            ft.hide(mCurrentFragment).show(fragment);
        }
        mCurrentFragment = fragment;
        mcurrentPage = pageIndex;
        //        ft.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
        ft.commitAllowingStateLoss();
        mCurrentPageIndex = pageIndex;
        //切换时做某些逻辑处理
        onPageSelected(mCurrentPageIndex);
    }

    private void onPageSelected(int position) {
    }

    /**
     * 恢复fragment
     */
    private void restoreFragment(){
        FragmentTransaction mBeginTreansaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if(i == mcurrentPage){
                mBeginTreansaction.show(fragments.get(i));
            }else{
                mBeginTreansaction.hide(fragments.get(i));
            }
        }
        mBeginTreansaction.commit();

        //把当前显示的fragment记录下来
        mCurrentFragment = fragments.get(mcurrentPage);
    }

    @OnClick({R.id.order, R.id.manage, R.id.community, R.id.workbench, R.id.hotList, R.id.mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order:
                mcurrentPage = PAGE_ORDER;
                switchFragment(PAGE_ORDER);
                break;
            case R.id.manage:
                mcurrentPage = PAGE_MANAGEMENT;
                switchFragment(PAGE_MANAGEMENT);
                break;
            case R.id.community:
                mcurrentPage = PAGE_COMMUNITY;
                switchFragment(PAGE_COMMUNITY);
                break;
            case R.id.workbench:
                mcurrentPage = PAGE_WORKENCH;
                switchFragment(PAGE_WORKENCH);
                break;
            case R.id.hotList:
                mcurrentPage = PAGE_HOT;
                switchFragment(PAGE_HOT);
                break;
            case R.id.mine:
                mcurrentPage = PAGE_MINE;
                switchFragment(PAGE_MINE);
                break;
        }
    }
}
