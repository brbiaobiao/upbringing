package com.teaching.upbringing.modular.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.teaching.upbringing.R;
import com.teaching.upbringing.manager.AppManager;
import com.teaching.upbringing.modular.community.CommunityFragment;
import com.teaching.upbringing.modular.home.HomeFragment;
import com.teaching.upbringing.modular.hot.HotFragment;
import com.teaching.upbringing.modular.mine.PersonlFragment;
import com.teaching.upbringing.modular.study.StudyFragment;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.ToastUtil;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMVPActivity {


    /**
     * 页面页码
     */
    public static final int PAGE_HOME = 0;
    public static final int PAGE_STUDY = 1;
    public static final int PAGE_COMMUNITY = 2;
    public static final int PAGE_HOT = 3;
    public static final int PAGE_MINE = 4;

    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";

    @BindView(R.id.fl_fragmentContent)
    FrameLayout mFlFragmentContent;
    @BindView(R.id.home)
    RadioButton mHome;
    @BindView(R.id.study)
    RadioButton mStudy;
    @BindView(R.id.community)
    RadioButton mCommunity;
    @BindView(R.id.hot)
    RadioButton mHot;
    @BindView(R.id.personal)
    RadioButton mPersonal;
    @BindView(R.id.main_btngroup)
    RadioGroup mMainBtngroup;

    private ArrayList<Fragment> fragments;
    private Fragment mCurrentFragment;
    private int mcurrentPage = 0;

    private HomeFragment homeFragment;
    private StudyFragment studyFragment;
    private CommunityFragment communityFragment;
    private HotFragment hotFragmentl;
    private PersonlFragment personlFragment;

    private int mCurrentPageIndex = -1;


    @Override
    protected Integer getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            mcurrentPage = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            //注意，添加顺序要跟下面添加的顺序一样！！！！
            if(fragments == null) {
                fragments = new ArrayList<>();
            }
            fragments.removeAll(fragments);
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_HOME+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_STUDY+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_COMMUNITY+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_HOME+""));
            fragments.add(getSupportFragmentManager().findFragmentByTag(PAGE_MINE+""));

            //恢复fragment页面
            restoreFragment();


        }else{      //正常启动时调用

            //初始化fragments
            fragments = new ArrayList<>();
            homeFragment = new HomeFragment();
            fragments.add(homeFragment);
            studyFragment = new StudyFragment();
            fragments.add(studyFragment);
            communityFragment = new CommunityFragment();
            fragments.add(communityFragment);
            hotFragmentl = new HotFragment();
            fragments.add(hotFragmentl);
            personlFragment = new PersonlFragment();
            fragments.add(personlFragment);

            switchFragment(PAGE_HOME);
        }
    }

    @OnClick({R.id.home, R.id.study, R.id.community, R.id.hot, R.id.personal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                mcurrentPage = PAGE_HOME;
                switchFragment(PAGE_HOME);
                break;
            case R.id.study:
                mcurrentPage = PAGE_STUDY;
                switchFragment(PAGE_STUDY);
                break;
            case R.id.community:
                mcurrentPage = PAGE_COMMUNITY;
                switchFragment(PAGE_COMMUNITY);
                break;
            case R.id.hot:
                mcurrentPage = PAGE_HOT;
                switchFragment(PAGE_HOT);
                break;
            case R.id.personal:
                mcurrentPage = PAGE_MINE;
                switchFragment(PAGE_MINE);
                break;
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
                ft.add(R.id.fl_fragmentContent, fragment, mcurrentPage+"");
            }else {
                ft.hide(mCurrentFragment).add(R.id.fl_fragmentContent, fragment, mcurrentPage+"");
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
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort("再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                AppManager.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
