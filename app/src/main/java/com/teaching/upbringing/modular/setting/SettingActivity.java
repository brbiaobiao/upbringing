package com.teaching.upbringing.modular.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.CityLevelEntity;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.modular.user.LoginActivity;
import com.teaching.upbringing.modular.user.UpdatePwdActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.GetJsonDataUtil;
import com.teaching.upbringing.utils.Navigation;
import com.teaching.upbringing.utils.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ChenHh
 * @time 2019/10/25 10:47
 * @des ${TODO}
 **/
public class SettingActivity extends BaseMVPActivity<SettingContract.IPresenter> implements SettingContract.IView {

    @BindView(R.id.tv_chang_pwd)
    TextView mTvChangPwd;
    @BindView(R.id.tv_common_add)
    TextView mTvCommonAdd;
    @BindView(R.id.tv_class_area_setting)
    TextView mTvClassAreaSetting;
    @BindView(R.id.tv_class_push)
    TextView mTvClassPush;
    @BindView(R.id.switch_release)
    Switch mSwitchRelease;
    @BindView(R.id.tv_login_out)
    TextView mTvLoginOut;
    private boolean isaAnalysisSuccess = false;
    private ArrayList<CityLevelEntity> options1Items;
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    public static void goIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        setTitleText("设置");
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        mSwitchRelease.setChecked(false);
        mSwitchRelease.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                ToastUtil.showShort("开");
            else
                ToastUtil.showShort("关");

        });
        initProvinceJson();//初始化城市数据
    }

    @Override
    protected SettingContract.IPresenter initPresenter() {
        return new SettingPresenter(this);
    }

    @OnClick({R.id.tv_chang_pwd, R.id.tv_common_add, R.id.tv_class_area_setting, R.id.tv_class_push, R.id.switch_release, R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chang_pwd:
                UpdatePwdActivity.goInto(this);
                break;
            case R.id.tv_common_add:
                Navigation.getInstance(this).toCommonAdd();
                break;
            case R.id.tv_class_area_setting:
                if (isaAnalysisSuccess) {
                    showPickerView();
                } else {
                    ToastUtil.showFault("暂未获取城市数据，请重新打开设置页面");
                }
                break;
            case R.id.tv_class_push:
                break;
            case R.id.tv_login_out:
                getPresenter().loginOut();
                break;
        }
    }

    @Override
    public void loginOut() {
        PreferenceManagers.saveValue(UserInfo.USERID, "");
        LoginActivity.goInto(this);
        finish();
    }

    private void initProvinceJson() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "city.json");//获取assets目录下的json文件数据

        ArrayList<CityLevelEntity> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCity().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                for (int i1 = 0; i1 < jsonBean.get(i).getCity().get(c).getArea().size(); i1++) {
                    String name = jsonBean.get(i).getCity().get(c).getArea().get(i1).getName();
                    city_AreaList.add(name);
                    province_AreaList.add(city_AreaList);//添加该省所有地区数据
                }
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }

    public ArrayList<CityLevelEntity> parseData(String result) {//Gson 解析
        ArrayList<CityLevelEntity> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityLevelEntity entity = gson.fromJson(data.optJSONObject(i).toString(), CityLevelEntity.class);
                detail.add(entity);
            }
            isaAnalysisSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            isaAnalysisSuccess = false;
            ToastUtil.showFault("解析数据失败");
        }
        return detail;
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                options2Items.get(options1).get(options2);
                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String province_id = options1Items.get(options1).getProvince_id();
                String city_id = options1Items.get(options1).getCity().get(options2).getId();
                String id = options1Items.get(options2).getCity().get(options2).getArea().get(options3).getId();
                String tx = opt1tx + province_id + "\n" + opt2tx + city_id + "\n" + opt3tx + id;
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
}
