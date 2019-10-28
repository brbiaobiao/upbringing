package com.outsourcing.library.manager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by bb
 * use to:自动加载更多管理
 * 使用普通RecyclerView.Adapter需要自行把数据list和adapter关联上
 *
 * @param <T> model
 */
public class LoadMoreManager<T> {
    //上次加载状态
    public static final int STATE_NORMAL = 0;//静止状态
    public static final int STATE_REFRESH = 1;//刷新
    public static final int STATE_LOADMORE = 2;//加载更多
    
    
    private Rule mRule;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView.Adapter mAdapter;
    private LoadMoreListener mListener;
    private KeyLoadMoreListener mKeyLoadMoreListener;
    private List<T> mList;
    
    private String mKey;
    private int mPagerIndex;
    private int mState = 0;
    private boolean isLoading;
    private boolean mIsEnd;
    private boolean mIsReversal;
    private boolean mEnableLoadMore = true;
    
    public LoadMoreManager(SmartRefreshLayout refreshLayout, RecyclerView.Adapter adapter) {
        this(refreshLayout, adapter, null, new Rule(1, 10));
    }
    
    public LoadMoreManager(SmartRefreshLayout refreshLayout, RecyclerView.Adapter adapter, List<T> dataList) {
        this(refreshLayout, adapter, dataList, new Rule(1, 10));
    }
    
    public LoadMoreManager(SmartRefreshLayout refreshLayout, RecyclerView.Adapter adapter, List<T> dataList, Rule rule) {
        init(refreshLayout, adapter, dataList, rule);
    }
    
    private void init(SmartRefreshLayout refreshLayout, RecyclerView.Adapter adapter, List<T> dataList, Rule rule) {
        mAdapter = adapter;
        mRule = rule;
        mPagerIndex = mRule.startIndex;
        mList = dataList;
        setRefreshLayout(refreshLayout);
    }
    
    private void setRefreshLayout(SmartRefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
        refreshLayout.setEnableAutoLoadMore(true);
        ClassicsHeader headerView = new ClassicsHeader(refreshLayout.getContext());
        refreshLayout.setRefreshHeader(headerView);
        ClassicsFooter footerView = new ClassicsFooter(refreshLayout.getContext());
        refreshLayout.setRefreshFooter(footerView);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (!mIsReversal) {
                    LoadMoreManager.this.loadMore();
                } else {
                    LoadMoreManager.this.refresh();
                }
            }
        
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (!mIsReversal) {
                    LoadMoreManager.this.refresh();
                } else {
                    LoadMoreManager.this.loadMore();
                }
            }
        });
    }
    
    /**
     * 如果有调用过某些涉及refreshLayout状态参数的api,这里重设并不会恢复这些状态
     */
    public void resetRefreshLayout(SmartRefreshLayout refreshLayout) {
        setRefreshLayout(refreshLayout);
    }
    
    /**
     * 列表反转,加载和刷新逻辑反转(未完成功能!需要查找修复bugs!很多情况还没有处理)
     */
    public void setReversal(boolean isReversal) {
        mIsReversal = isReversal;
    }
    
    protected final void refresh() {
        mState = STATE_REFRESH;
        isLoading = true;
        mPagerIndex = mRule.startIndex;
        mRefreshLayout.setEnableAutoLoadMore(true);
        
        if (mPagerIndex <= 0) {//页面的起始index是1
            mPagerIndex = mRule.startIndex;
        }
        
        if (mListener != null) {
            mListener.onLoadMore(mRule.getPagerSize(), mPagerIndex);
        }
        
        if (mKeyLoadMoreListener != null) {
            mKeyLoadMoreListener.onLoadMore(mKey, mRule.getPagerSize(), mPagerIndex);
        }
        onRefresh();
    }
    
    protected final void loadMore() {
        mState = STATE_LOADMORE;
        isLoading = true;
        if (mListener != null) {
            mListener.onLoadMore(mRule.getPagerSize(), mPagerIndex);
        }
        if (mKeyLoadMoreListener != null) {
            mKeyLoadMoreListener.onLoadMore(mKey, mRule.getPagerSize(), mPagerIndex);
        }
        onLoadMore();
    }
    
    /**
     * 刷新回调
     */
    public void onRefresh() {
        setLockLoadMore(mEnableLoadMore);
    }
    
    /**
     * 加载回调
     */
    public void onLoadMore() {
    }
    
    
    public void autoRefresh() {
        if (mIsReversal) {
            mRefreshLayout.autoLoadMore();
        } else {
            mRefreshLayout.autoRefresh();
        }
    }
    
    /**
     * 加载监听
     */
    public void setOnLoadMoreLinstener(LoadMoreListener linstener) {
        mListener = linstener;
    }
    
    /**
     * 有key的加载监听
     */
    public void setOnKeyLoadMoreLinetener(KeyLoadMoreListener linetener) {
        mKeyLoadMoreListener = linetener;
    }
    
    
    public void setKey(String key) {
        mKey = key;
    }
    
    public String getKey() {
        return mKey;
    }
    
    /**
     * 没有更多数据了,并且禁用加载更多
     */
    public void noMoreData() {
        mIsEnd = true;
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableAutoLoadMore(false);
//        notifyAdapter();
    }
    
    /**
     * 清空页面数据
     */
    public void clearData() {
        mPagerIndex = mRule.startIndex;
        if (mList != null) {
            mList.clear();
        }
        notifyAdapter();
    }
    
    /**
     * 加载完成后手动回调此方法隐藏头脚
     */
    public void loadFinish() {
        isLoading = false;
        if (mState == STATE_LOADMORE) {
            mRefreshLayout.finishLoadMore();
        } else if (mState == STATE_REFRESH) {
            mRefreshLayout.finishRefresh();
        }
        mState = STATE_NORMAL;
    }
    
    public void addData(List<T> list) {
        if (mPagerIndex == mRule.startIndex) {
            if (mList == null) {
                mList = new ArrayList<>();
            } else {
                mList.clear();
            }
        }
        
        if (mList.isEmpty() && (list == null || list.isEmpty())) {//没有任何数据
            noMoreData();
            notifyAdapter();
            return;
        }
        mList.addAll(list);
        
        if (list.size() < mRule.getPagerSize()) {
            mIsEnd = true;
            if (mPagerIndex != mRule.startIndex) {
            }
            noMoreData();
            notifyAdapter();
        } else {
            mIsEnd = false;
            
            notifyAdapter();
        }
        mPagerIndex++;
    }
    
    public boolean isEmptyData() {
        return mList == null || mList.isEmpty();
    }
    
    private void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }
    
    public void notifyItemChange(int position) {
        mAdapter.notifyItemChanged(position);
    }
    
    /**
     * 设置是否锁定加载更多
     */
    private void setLockLoadMore(boolean isLock) {
        mIsEnd = isLock;
        mRefreshLayout.setEnableLoadMore(isLock)
                .setEnableAutoLoadMore(isLock);
    }
    
    public void setLoadMoreEnable(boolean enable) {
        mEnableLoadMore = enable;
        setLockLoadMore(enable);
    }
    
    public void setRefreshEnable(boolean enable) {
        mRefreshLayout.setEnableRefresh(enable);
    }
    
    /**
     * 常量存放
     */
    public static class Rule {
        
        protected int pageSize = 10;
        protected int startIndex = 0;
        
        public Rule(int startIndex, int pageSize) {
            this.startIndex = startIndex;
            this.pageSize = pageSize;
        }
        
        public int getPagerSize() {
            return pageSize;
        }
    }
    
    public interface LoadMoreListener {
        void onLoadMore(int pageSize, int index);
    }
    
    public interface KeyLoadMoreListener {
        void onLoadMore(String key, int pageSize, int index);
    }
    
    public int getState() {
        return mState;
    }
}
