package com.teaching.upbringing.modular.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.outsourcing.library.utils.GsonUtil;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.R;
import com.teaching.upbringing.adapter.SearchAddressAdapter;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.address.DatasKey;
import com.teaching.upbringing.utils.address.OnItemClickLisenter;
import com.teaching.upbringing.utils.address.SPUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bb
 * @time 2019/11/8 17:37
 * @des ${TODO}
 **/
public class SearchAddressActivity extends BaseMVPActivity<SearchAddressContract.IPresenter> implements SearchAddressContract.IView {
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_search_address)
    RecyclerView mRvSearchAddress;
    @BindView(R.id.actionbar_back)
    TextView mActionbarBack;

    private List<PoiItem> mList;
    private SearchAddressAdapter mSearchAddressAdapter;

    private PoiSearch mPoiSearch;
    private PoiSearch.Query mQuery;
    private PoiSearch.OnPoiSearchListener mOnPoiSearchListener;
    private OnItemClickLisenter mOnItemClickLisenter;
    public AMapLocation location;

    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, SearchAddressActivity.class);
        return intent;
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_search_address;
    }

    @Override
    protected void init() {
        hideHeadBar();
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        mEtSearch.requestFocus();
        mEtSearch.setFocusable(true);
        mEtSearch.setFocusableInTouchMode(true);
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        initData();
        initListener();
    }

    private void initData() {
        mList = new ArrayList<>();
        mSearchAddressAdapter = new SearchAddressAdapter(this, mList);
        mRvSearchAddress.setLayoutManager(new LinearLayoutManager(this));
        mRvSearchAddress.setAdapter(mSearchAddressAdapter);
    }

    private void initListener() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (null != editable) {
                    if (0 == editable.length()) {//没有输入则清空搜索记录
                        mList.clear();
                        mSearchAddressAdapter.setList(mList, "");
                    } else {
                        if (null != location) {
                            doSearchQuery(editable.toString(), location.getCity(), new LatLonPoint(location.getLatitude(), location.getLongitude()));
                        } else {
                            doSearchQuery(editable.toString(), "", null);
                        }
                    }
                }
            }
        });

        mOnItemClickLisenter = position -> {
            PoiItem poiItem = mList.get(position);
            if (null != poiItem) {//获取信息并回传上一页面
                KeyboardUtils.hideSoftInput(this);
                Intent intent = new Intent();
                intent.putExtra(DatasKey.SEARCH_INFO, poiItem);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
        mSearchAddressAdapter.setOnItemClickLisenter(mOnItemClickLisenter);

        mOnPoiSearchListener = new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int i) {
                if (i == 1000) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(mQuery)) {// 是否是同一条
                            if (null != mList) {
                                mList.clear();
                            }
                            mList.addAll(result.getPois());// 取得第一页的poiitem数据，页数从数字0开始
                            if (null != mSearchAddressAdapter) {
                                mSearchAddressAdapter.setList(mList, mEtSearch.getText().toString().trim());
                                mRvSearchAddress.smoothScrollToPosition(0);
                            }
                        }
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        };
        mActionbarBack.setOnClickListener(view -> finish());
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord, String city, LatLonPoint lpTemp) {
        mQuery = new PoiSearch.Query(keyWord, "", city);//第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mQuery.setPageSize(20);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(0);// 设置查第一页


        mPoiSearch = new PoiSearch(this, mQuery);
        mPoiSearch.setOnPoiSearchListener(mOnPoiSearchListener);
        if (lpTemp != null) {
            mPoiSearch.setBound(new PoiSearch.SearchBound(lpTemp, 10000, true));//该范围的中心点-----半径，单位：米-----是否按照距离排序
        }
        mPoiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == location) {
            String s = SPUtils.getString(this, DatasKey.LOCATION_INFO);//获取保存的定位信息
            if (!TextUtils.isEmpty(s)) {
                try {
                    location = GsonUtil.getIntance().fromJson(s, AMapLocation.class);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPoiSearch) {
            mPoiSearch = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
