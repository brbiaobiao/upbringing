package com.teaching.upbringing.modular.address;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.R;
import com.teaching.upbringing.adapter.AllCityAdapter;
import com.teaching.upbringing.adapter.CityHistotyAdapter;
import com.teaching.upbringing.adapter.LocationAddrCityAdapter;
import com.teaching.upbringing.adapter.ReLocationAddrCityAdapter;
import com.teaching.upbringing.entity.AllCityEntity;
import com.teaching.upbringing.entity.CityHitstory;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;
import com.teaching.upbringing.manager.UniqueSignManaga;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.AnimationUtil;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.utils.ToastUtil;
import com.teaching.upbringing.widget.QuickIndex;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author bb
 * @time 2019/10/29 10:33
 * @des ${TODO}
 **/
public class LocationAddrActivity extends BaseMVPActivity<LocationAddrContract.IPresenter> implements LocationAddrContract.IView {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;
    @BindView(R.id.tv_location_at_now)
    TextView mTvLocationAtNow;
    @BindView(R.id.iv_location)
    ImageView mIvLocation;
    @BindView(R.id.tvLocation_title)
    TextView mTvLocationTitle;
    @BindView(R.id.tv_relocation)
    TextView mTvRelocation;
    @BindView(R.id.rv_city)
    RecyclerView mRvCity;
    @BindView(R.id.rv_all_city)
    RecyclerView mRvAllCity;
    @BindView(R.id.quickindex)
    QuickIndex mQuickindex;
    @BindView(R.id.cl_location)
    ConstraintLayout mClLocation;
    @BindView(R.id.rl_city)
    RelativeLayout mRlCity;
    @BindView(R.id.letter_tv)
    TextView mLetterTv;
    @BindView(R.id.iv_switch_area)
    ImageView mIvSwitchArea;
    @BindView(R.id.switch_area)
    TextView mSwitchArea;
    @BindView(R.id.rv_area)
    RecyclerView mRvArea;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.tv_city_history)
    TextView mTvCityHistory;
    @BindView(R.id.rv_city_history)
    RecyclerView mRvCityHistory;


    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private AMapLocation location;
    private AMapLocationListener mAMapLocationListener;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};

    private LocationAddrCityAdapter adapter;
    private AllCityAdapter allCityAdapter;
    private LinearLayoutManager mLayoutManager;

    private ReLocationAddrCityAdapter reLocationAddrCityAdapter;
    private CityHistotyAdapter cityHistotyAdapter;
    private int switchB = 0;

    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, LocationAddrActivity.class);
        return intent;
    }

    @Override
    protected LocationAddrContract.IPresenter initPresenter() {
        return new LocationAddrPresenter(this);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_locationaddr;
    }

    @Override
    protected void init() {
        hideHeadBar();
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        mTvLocationTitle.setText("定位中......");
        mLayoutManager = new LinearLayoutManager(this);
        mRvCity.setLayoutManager(new LinearLayoutManager(this));
        mRvAllCity.setLayoutManager(mLayoutManager);
        adapter = new LocationAddrCityAdapter(null);
        mRvCity.setAdapter(adapter);
        allCityAdapter = new AllCityAdapter(null);
        mRvAllCity.setAdapter(allCityAdapter);
        mRvArea.setLayoutManager(new GridLayoutManager(this, 3));
        mRvArea.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        reLocationAddrCityAdapter = new ReLocationAddrCityAdapter(null);
        mRvArea.setAdapter(reLocationAddrCityAdapter);
//        showCityHistory();
        getPresenter().getListAllRegion();
        initListener();
        initPermission();
    }

    private void showCityHistory(){
        List<CityHitstory> cityHitstoryList = PreferenceManagers.getListObject(UserInfo.CITY_HISTORY, CityHitstory.class);
        if(cityHitstoryList == null || cityHitstoryList.size() == 0) {
            mLine.setVisibility(View.GONE);
            mTvCityHistory.setVisibility(View.GONE);
            mRvCityHistory.setVisibility(View.GONE);
        }else {
            mLine.setVisibility(View.VISIBLE);
            mTvCityHistory.setVisibility(View.VISIBLE);
            mRvCityHistory.setVisibility(View.VISIBLE);
        }
        mRvCityHistory.setLayoutManager(new GridLayoutManager(this, 3));
        mRvCityHistory.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        cityHistotyAdapter = new CityHistotyAdapter(cityHitstoryList);
        mRvCityHistory.setAdapter(cityHistotyAdapter);
    }

    private void initListener() {
        //gps定位监听器
        mAMapLocationListener = loc -> {
            try {
                if (null != loc) {
                    stopLocation();
                    if (loc.getErrorCode() == 0) {//可在其中解析amapLocation获取相应内容。
                        mTvRelocation.setVisibility(View.GONE);
                        location = loc;
                        doWhenLocationSucess();
                    } else {
                        mTvRelocation.setVisibility(View.VISIBLE);
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        showToastWithErrorInfo(loc.getErrorCode());
                        Log.e("AmapError", "location Error, ErrCode:"
                                + loc.getErrorCode() + ", errInfo:"
                                + loc.getErrorInfo());

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
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
                        mClLocation.setVisibility(View.VISIBLE);
                        mRlCity.setVisibility(View.VISIBLE);
                        mRvCity.setVisibility(View.GONE);
                    } else {
                        mClLocation.setVisibility(View.GONE);
                        mRlCity.setVisibility(View.GONE);
                        mRvCity.setVisibility(View.VISIBLE);
                        getPresenter().listAllRegionByNameLike(mEtSearch.getText().toString().trim());
                    }
                }
            }
        });

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            ListAllRegionByNameEntity listAllRegionByNameEntity = (ListAllRegionByNameEntity) adapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra(UniqueSignManaga.CITY_NAME, listAllRegionByNameEntity.getName());
            setResult(RESULT_OK, intent);
            KeyboardUtils.hideSoftInput(LocationAddrActivity.this);
            finish();
        });

        allCityAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AllCityEntity allCityEntity = (AllCityEntity) adapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra(UniqueSignManaga.CITY_NAME, allCityEntity.getName());
            setResult(RESULT_OK, intent);
            KeyboardUtils.hideSoftInput(LocationAddrActivity.this);
            finish();
        });
        reLocationAddrCityAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.area_name:
                    ListAllRegionByNameEntity listAllRegionByNameEntity = (ListAllRegionByNameEntity) adapter.getData().get(position);
                    Intent intent = new Intent();
                    intent.putExtra(UniqueSignManaga.CITY_NAME, listAllRegionByNameEntity.getName());
                    setResult(RESULT_OK, intent);
                    KeyboardUtils.hideSoftInput(LocationAddrActivity.this);
                    finish();
                    break;
            }
        });
        /*cityHistotyAdapter.setOnItemChildClickListener((adapter,view,position)->{
            CityHitstory cityHitstory = (CityHitstory) adapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra(UniqueSignManaga.CITY_NAME, cityHitstory.getCity_name());
            setResult(RESULT_OK, intent);
            KeyboardUtils.hideSoftInput(LocationAddrActivity.this);
            finish();
        });*/
        mIvSwitchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchB==0) {
                    mRvArea.setVisibility(View.VISIBLE);
                    mRvArea.setAnimation(AnimationUtil.moveToViewLocation());
                    mIvSwitchArea.setImageResource(R.mipmap.icon_up_triangle);
                    switchB=1;
                } else {
                    mRvArea.setVisibility(View.GONE);
                    mIvSwitchArea.setImageResource(R.mipmap.icon_down_triangle);
                    switchB=0;
                }
            }
        });

        mQuickindex.setOnLetterUpdateListener(s -> getPresenter().onLetterUpdate(s));
    }

    private void initPermission() {

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);
            int l = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[1]);
            int m = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[2]);
            int n = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[3]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED || l != PackageManager.PERMISSION_GRANTED || m != PackageManager.PERMISSION_GRANTED ||
                    n != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, permissions, 321);
            } else {
                startLocation();
            }
        }
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setMockEnable(true);//如果您希望位置被模拟，请通过setMockEnable(true);方法开启允许位置模拟
        return mOption;
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        if (null == locationClient) {
            //初始化client
            locationClient = new AMapLocationClient(this.getApplicationContext());
            //设置定位参数
            locationClient.setLocationOption(getDefaultOption());
            // 设置定位监听
            locationClient.setLocationListener(mAMapLocationListener);
        }
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        initLocation();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        if (null != locationClient) {
            locationClient.stopLocation();
        }
    }

    /**
     * 当定位成功需要做的事情
     */
    private void doWhenLocationSucess() {
        if (StringUtils.isEmpty(location.getDistrict())) {
            mTvLocationTitle.setText(location.getCity());
        } else {
            mTvLocationTitle.setText(location.getCity() + location.getDistrict());
        }
        getPresenter().listAreaRegionByCityCityName(location.getCity());
    }

    private void showToastWithErrorInfo(int error) {
        String tips = "定位错误码：" + error;
        switch (error) {
            case 4:
                tips = "请检查设备网络是否通畅";
                break;
            case 7:
                tips = "请仔细检查key绑定的sha1值与apk签名sha1值是否对应。";
                break;
            case 12:
                tips = "请在设备的设置中开启app的定位权限。";
                break;
            case 18:
                tips = "建议手机关闭飞行模式，并打开WIFI开关";
                break;
            case 19:
                tips = "建议手机插上sim卡，打开WIFI开关";
                break;
        }
        ToastUtil.showShort(tips);
    }

    /**
     * 用户权限 申请 的回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                    ToastUtil.showShort("请开启权限");
                } else {
                    //获取权限成功
                    startLocation();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocation();
        if (null != locationClient) {
            locationClient.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyboardUtils.hideSoftInput(LocationAddrActivity.this);
    }

    @OnClick({R.id.et_search, R.id.tv_relocation, R.id.tv_back, R.id.switch_area,R.id.iv_switch_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                mRvCity.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_relocation:
                mTvLocationTitle.setText("定位中.....");
                startLocation();
                break;
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.switch_area:
                if (switchB==0) {
                    mRvArea.setVisibility(View.VISIBLE);
                    mIvSwitchArea.setImageResource(R.mipmap.icon_up_triangle);
                    mRvArea.setAnimation(AnimationUtil.moveToViewLocation());
                    switchB=1;
                } else {
                    mRvArea.setVisibility(View.GONE);
                    mIvSwitchArea.setImageResource(R.mipmap.icon_down_triangle);
                    switchB=0;
                }
                break;

        }
    }

    @Override
    public void setCityAdapter(List<ListAllRegionByNameEntity> list) {
        if (adapter == null) {
            adapter = new LocationAddrCityAdapter(list);
            mRvCity.setAdapter(adapter);
        } else {
            adapter.setNewData(list);
        }
    }

    @Override
    public void setAllCityAdapter(List<AllCityEntity> allCityEntities) {
        if (allCityAdapter == null) {
            allCityAdapter = new AllCityAdapter(allCityEntities);
            mRvAllCity.setAdapter(allCityAdapter);
        } else {
            allCityAdapter.setNewData(allCityEntities);
        }
    }

    @Override
    public void letterUpdate(int position) {
        if (mLayoutManager == null)
            return;
        mLayoutManager.scrollToPositionWithOffset(position,0);
        mLayoutManager.setStackFromEnd(true);
    }

    @Override
    public void setLetterText(String letterText) {
        mLetterTv.setVisibility(View.VISIBLE);
        mLetterTv.setText(letterText);

        mLetterTv.postDelayed(() -> mLetterTv.setVisibility(View.GONE), 1000);
    }

    @Override
    public void setReCityAdapter(List<ListAllRegionByNameEntity> list) {
        if (list == null || list.size() == 0) {
            mIvSwitchArea.setVisibility(View.GONE);
            mSwitchArea.setVisibility(View.GONE);
        } else {
            mIvSwitchArea.setVisibility(View.VISIBLE);
            mSwitchArea.setVisibility(View.VISIBLE);
        }
        if (reLocationAddrCityAdapter == null) {
            reLocationAddrCityAdapter = new ReLocationAddrCityAdapter(list);
            mRvArea.setAdapter(reLocationAddrCityAdapter);
        } else {
            reLocationAddrCityAdapter.setNewData(list);
        }
    }
}
