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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lefore.tutoring.R;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.adapter.LocationAddrCityAdapter;
import com.teaching.upbringing.adapter.ReLocationAddrCityAdapter;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;
import com.teaching.upbringing.manager.UniqueSignManaga;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.AnimationUtil;
import com.teaching.upbringing.utils.ToastUtil;
import com.teaching.upbringing.widget.GridSpacingItemDecoration;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author ChenHh
 * @time 2019/11/10 5:30
 * @des
 **/
public class ReLocationAddrActivity extends BaseMVPActivity<ReLocationAddrContract.IPresenter> implements ReLocationAddrContract.IView {
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
    @BindView(R.id.line1)
    View mLine1;
    @BindView(R.id.tv_location_at_now_two)
    TextView mTvLocationAtNowTwo;
    @BindView(R.id.iv_location_two)
    ImageView mIvLocationTwo;
    @BindView(R.id.tvLocation_title_two)
    TextView mTvLocationTitleTwo;
    @BindView(R.id.switch_area)
    TextView mSwitchArea;
    @BindView(R.id.rv_area)
    RecyclerView mRvArea;
    @BindView(R.id.sl_location_city)
    ScrollView mSlLocationCity;
    @BindView(R.id.rv_city)
    RecyclerView mRvCity;
    @BindView(R.id.iv_switch_area)
    CheckBox mIvSwitchArea;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private AMapLocation location;
    private AMapLocationListener mAMapLocationListener;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};

    private LocationAddrCityAdapter addrCityAdapter;
    private ReLocationAddrCityAdapter reLocationAddrCityAdapter;

    private boolean isShowArea = false;

    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, ReLocationAddrActivity.class);
        return intent;
    }

    @Override
    protected ReLocationAddrContract.IPresenter initPresenter() {
        return new ReLocationAddrPresenter(this);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_relocationaddr;
    }

    @Override
    protected void init() {
        hideHeadBar();
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        mTvLocationTitle.setText("定位中......");
        mRvCity.setLayoutManager(new LinearLayoutManager(this));
        mRvArea.setLayoutManager(new GridLayoutManager(this, 3));
        mRvArea.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        mRvArea.setHasFixedSize(true);
        mRvArea.setNestedScrollingEnabled(false);
        addrCityAdapter = new LocationAddrCityAdapter(null);
        mRvCity.setAdapter(addrCityAdapter);
        reLocationAddrCityAdapter = new ReLocationAddrCityAdapter(null);
        mRvArea.setAdapter(reLocationAddrCityAdapter);
        initListener();
        initPermission();
    }

    private void initListener() {
        //gps定位监听器
        mAMapLocationListener = loc -> {
            try {
                if (null != loc) {
                    stopLocation();
                    if (loc.getErrorCode() == 0) {//可在其中解析amapLocation获取相应内容。
                        location = loc;
                        doWhenLocationSucess();
                    } else {
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
                        mSlLocationCity.setVisibility(View.VISIBLE);
                        mRvCity.setVisibility(View.GONE);
                    } else {
                        mSlLocationCity.setVisibility(View.GONE);
                        mRvCity.setVisibility(View.VISIBLE);
                        getPresenter().listAllRegionByNameLike(mEtSearch.getText().toString().trim());
                    }
                }
            }
        });

        addrCityAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ListAllRegionByNameEntity listAllRegionByNameEntity = (ListAllRegionByNameEntity) adapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra(UniqueSignManaga.CITY_NAME, listAllRegionByNameEntity.getName());
            setResult(RESULT_OK, intent);
            KeyboardUtils.hideSoftInput(ReLocationAddrActivity.this);
            finish();
        });

        reLocationAddrCityAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case  R.id.area_name:
                    ListAllRegionByNameEntity listAllRegionByNameEntity = (ListAllRegionByNameEntity) adapter.getData().get(position);
                    Intent intent = new Intent();
                    intent.putExtra(UniqueSignManaga.CITY_NAME, listAllRegionByNameEntity.getName());
                    setResult(RESULT_OK, intent);
                    KeyboardUtils.hideSoftInput(ReLocationAddrActivity.this);
                    finish();
                    break;
            }
        });

        mIvSwitchArea.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b) {
                mRvArea.setVisibility(View.VISIBLE);
                mRvArea.setAnimation(AnimationUtil.moveToViewLocation());
            }else {
                mRvArea.setVisibility(View.GONE);
            }
        });

        mTvBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyboardUtils.hideSoftInput(ReLocationAddrActivity.this);
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
        mTvLocationTitle.setText(location.getCity());
        mTvLocationTitleTwo.setText(location.getCity() + location.getDistrict());
//        getPresenter().listAreaRegionByCityName(location.getCityCode());
        getPresenter().listAreaRegionByCityCityName(location.getCity());
    }

    private void showToastWithErrorInfo(int error) {
        String tips = "定位错误码：" + error;
        switch (error) {
            case 4:
                tips = "请检查设备网络是否通畅，检查通过接口设置的网络访问超时时间，建议采用默认的30秒。";
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
    public void setCityAdapter(List<ListAllRegionByNameEntity> list) {
        if (addrCityAdapter == null) {
            addrCityAdapter = new LocationAddrCityAdapter(list);
            mRvCity.setAdapter(addrCityAdapter);
        } else {
            addrCityAdapter.setNewData(list);
        }
    }

    @Override
    public void setReCityAdapter(List<ListAllRegionByNameEntity> list) {
        if (reLocationAddrCityAdapter == null) {
            reLocationAddrCityAdapter = new ReLocationAddrCityAdapter(list);
            mRvArea.setAdapter(reLocationAddrCityAdapter);
        } else {
            reLocationAddrCityAdapter.setNewData(list);
        }
    }
}
