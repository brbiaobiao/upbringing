package com.teaching.upbringing.modular.address;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.OnResultUtil;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author bb
 * @time 2019/11/7 15:10
 * @des ${TODO}
 **/
public class AddAddressActivity extends BaseMVPActivity<AddAddressContract.IPresenter> implements AddAddressContract.IView {

    public static final String COMMONADDENTITY = "commonAddEntity";

    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.ll_location)
    LinearLayout mLlLocation;
    @BindView(R.id.et_house_name)
    EditText mEtHouseName;
    @BindView(R.id.ll_house_name)
    LinearLayout mLlHouseName;
    @BindView(R.id.tv_default_add)
    TextView mTvDefaultAdd;
    @BindView(R.id.switch_defalut)
    Switch mSwitchDefalut;

    private boolean isNew = true;

    private boolean is_defalt_add;
    private Map<String, Object> map = new HashMap<>();
    private String title;
    private String location;
    private long id;

    public static Intent getCallIntent(Context context, CommonAddEntity commonAddEntity) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra(COMMONADDENTITY,commonAddEntity);
        return intent;
    }

    @Override
    protected AddAddressContract.IPresenter initPresenter() {
        return new AddAddressPresenter(this);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void init() {
        setTitleText("添加地址");
        StatusBarUtil.setStatusBarColor(this,R.color.white);
        map.put("ifDefault", false);//默认传入false,表示不为默认地址
        mSwitchDefalut.setOnCheckedChangeListener((buttonView, isChecked) -> {
            is_defalt_add = isChecked;//选中为true
            map.put("ifDefault", is_defalt_add);
        });
        Intent intent = getIntent();
        CommonAddEntity commonAddEntity  = (CommonAddEntity) intent.getSerializableExtra(COMMONADDENTITY);
        isNew = commonAddEntity == null;
        initData(commonAddEntity);
    }

    private void initData(CommonAddEntity commonAddEntity){
        if(commonAddEntity == null) {
            return;
        }
        mTvLocation.setText(commonAddEntity.getName());
        mEtHouseName.setText(commonAddEntity.getHouseNumber());
        is_defalt_add = commonAddEntity.isIfDefault();
        title = commonAddEntity.getName();
        location = commonAddEntity.getLocation();
        id = commonAddEntity.getId();
    }

    @Override
    public void afterAdd() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public String getLoaction() {
        return mTvLocation.getText().toString().trim();
    }

    @Override
    public String getHouseName() {
        return mEtHouseName.getText().toString().trim();
    }

    @OnClick({R.id.ll_location, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_location:
                new OnResultUtil(this).call(SelectAddressActivity.goCallIntent(this,location))
                        .filter(info->OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> {
                            PoiItem poiItem = activityResultInfo.getData().getParcelableExtra(SelectAddressActivity.SELECT_ADDRESS_POITEM);
                            title = poiItem.getTitle();
                            mTvLocation.setText(title);
//                            "纬度：" + poiItem.getLatLonPoint().getLatitude() + "  " + "经度：" + poiItem.getLatLonPoint().getLongitude()
                            location = poiItem.getLatLonPoint().getLatitude() +","+
                                    poiItem.getLatLonPoint().getLongitude();
                        });
                break;
            case R.id.tv_save:
                map.put("name", title);
                map.put("location", location);
                map.put("houseNumber",getHouseName());
                if(!isNew) {
                    map.put("id",id);
                }
                getPresenter().addOrUpdataAddress(isNew,map);
                KeyboardUtils.hideSoftInput(this);
                break;
        }
    }
}
