package com.teaching.upbringing.mvpBase;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lefore.tutoring.R;
import com.outsourcing.library.manager.LoadMoreManager;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author bb
 * @time 2019/10/28 16:35
 * @des ${TODO}
 **/
public abstract class BaseListFragment<T extends IBasePresenter,M> extends BaseMVPFragment<T> {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_empty_view)
    TextView mTvEmptyView;
    @BindView(R.id.swipe_sl)
    SmartRefreshLayout mSwipeSl;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;

    private BaseListAdapter<M> mAdapter;
    private LoadMoreManager<M> mLoadMoreManager;
    private List<M> mDataList;//destroyView也会缓存列表
    private boolean mHasHeader;
    private boolean mHasFooter;
    private boolean mNeedItemClick = true;
    private boolean mIsAutoRefresh = true;


    /**
     * 获取adapter
     *
     * @param dataList
     */
    protected abstract BaseListAdapter<M> initListAdapter(List<M> dataList);

    protected abstract void requestData(String key, int pageSize, int index);

    protected void onItemClick(BaseListAdapter<M> adapter, View view, int position) {
    }


    @Override
    protected Integer getContentId() {
        return R.layout.activity_base_list;
    }

    protected RecyclerView.LayoutManager initLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected void init() {
        //没有数据,代表需要全部初始化
        boolean isUseCache = false;
        if (mDataList == null) {
            mDataList = new ArrayList<>();
            mAdapter = initListAdapter(mDataList);
            mRvList.setAdapter(mAdapter);
            mRvList.setLayoutManager(initLayoutManager());
            if (mNeedItemClick) {
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        try {
                            BaseListFragment.this.onItemClick((BaseListAdapter<M>) adapter, view, position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            mLoadMoreManager = new LoadMoreManager<>(mSwipeSl, mAdapter, mDataList);
            mLoadMoreManager.setOnKeyLoadMoreLinetener(new LoadMoreManager.KeyLoadMoreListener() {
                @Override
                public void onLoadMore(String key, int pageSize, int index) {
                    requestData(key, pageSize, index);
                }
            });

        } else {
            isUseCache = true;
            mRvList.setAdapter(mAdapter);
            mRvList.setLayoutManager(initLayoutManager());
            mLoadMoreManager.resetRefreshLayout(mSwipeSl);
        }
        //mLoadMoreManager.setReversal(isReversal());
        if (!isUseCache && mIsAutoRefresh) {
            autoRefresh();
        } else {
            handleEmptyView();
        }
    }

    /**
     * 是否列表翻转,如果return true, 要同时重写{@link #initLayoutManager}才能够实现真正的列表翻转
     */
    //    protected boolean isReversal() {
    //        return false;
    //    }
    protected void setNeedItemClick(boolean need) {
        mNeedItemClick = need;
    }

    protected void autoRefresh() {
        mLoadMoreManager.autoRefresh();
    }

    public void setKey(String key) {
        mLoadMoreManager.setKey(key);
    }

    protected String getKey() {
        return mLoadMoreManager.getKey();
    }

    public void addData(List<M> list) {
        mLoadMoreManager.addData(list);
        handleEmptyView();
    }

    protected BaseListAdapter<M> getListAdapter() {
        return mAdapter;
    }

    /**
     * 调用此方法表示结束加载
     */
    public void refreshFinish() {
        mLoadMoreManager.loadFinish();
    }

    public void setAutoRefresh(boolean autoRefresh) {
        mIsAutoRefresh = autoRefresh;
    }


    public void cleanData() {
        mLoadMoreManager.clearData();
        handleEmptyView();
    }

    private void handleEmptyView() {
        if (mLoadMoreManager.isEmptyData()) {
            showEmptyView();
        } else {
            showListView();
        }
    }

    public void addHeader(View view) {
        if (!mHasHeader) {
            mLlContent.addView(view, 0);
            mHasHeader = true;
        }
    }

    public void addFooter(View view) {
        if (!mHasFooter) {
            int childCount = mLlContent.getChildCount();
            mLlContent.addView(view, childCount);
            mHasFooter = true;
        }
    }

    protected RecyclerView getListView() {
        return mRvList;
    }

    protected SmartRefreshLayout getRefreshView() {
        return mSwipeSl;
    }

    public void refresh() {
        if (mSwipeSl != null)
            mSwipeSl.autoRefresh();
    }

    protected void showEmptyView() {
        mTvEmptyView.setVisibility(View.VISIBLE);
        mRvList.setVisibility(View.GONE);
    }

    protected void showListView() {
        mTvEmptyView.setVisibility(View.GONE);
        mRvList.setVisibility(View.VISIBLE);
    }

    protected List<M> getDataList() {
        return mDataList;
    }

    protected void noMoreData() {
        mLoadMoreManager.noMoreData();
    }

    protected LoadMoreManager<M> getListLoadMoreManager() {
        return mLoadMoreManager;
    }

    protected void setBackgroundColor(int color) {
        mLlContent.setBackgroundColor(color);
    }
}
