package com.teaching.upbringing.modular.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teaching.upbringing.adapter.ClassAreaAdapter;
import com.teaching.upbringing.entity.CityLevelEntity;
import com.teaching.upbringing.entity.ClassAreaEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.GetJsonDataUtil;
import com.teaching.upbringing.utils.ToastUtil;
import com.teaching.upbringing.widget.dialog.ConfigurableDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ChenHh
 * @time 2019/11/26 0:21
 * @des 上课区域
 **/
public class ClassAreaActivity extends BaseMVPActivity<ClassAreaContract.IPresenter> implements ClassAreaContract.IView {

    @BindView(R.id.tv_empty_view)
    TextView mTvEmptyView;
    @BindView(R.id.rv_class_area)
    RecyclerView mRvClassArea;
    @BindView(R.id.tv_add_class_area)
    TextView mTvAddClassArea;
    @BindView(R.id.smart_rel)
    SmartRefreshLayout mSmartRel;

    private ClassAreaAdapter adapter;
    private List<ClassAreaEntity> classAreaEntitys = new ArrayList<>();

    private boolean isaAnalysisSuccess = false;
    private ArrayList<CityLevelEntity> options1Items;
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    public static final void goInto(Context context) {
        Intent intent = new Intent(context, ClassAreaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ClassAreaContract.IPresenter initPresenter() {
        return new ClassAreaPresenter(this);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_class_area;
    }

    @Override
    protected void init() {
        setTitleText("上课区域");
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        mTvAddClassArea.setText("添加上课区域");
        mRvClassArea.setLayoutManager(new LinearLayoutManager(this));
        mSmartRel.autoRefresh();
        mSmartRel.setOnRefreshListener(refreshLayout -> getPresenter().getClassList());
        initProvinceJson();//初始化城市数据
        initEnent();
    }

    @OnClick(R.id.tv_add_class_area)
    public void onViewClicked() {
        if (isaAnalysisSuccess) {
            showPickerView();
        } else {
            ToastUtil.showFault("暂未获取城市数据，请重新打开设置页面");
        }
    }

    private void initEnent(){
        mRvClassArea.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case  R.id.tvDelete_edittask_item:
                        getPresenter().removeClassArea(position);
                        break;
                }
            }
        });
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
                String area_name = "";
                try {
                    for (int j = 0; j < jsonBean.get(i).getCity().get(c).getArea().size(); j++) {
                        area_name = jsonBean.get(i).getCity().get(c).getArea().get(j).getName();
                        String name = jsonBean.get(i).getCity().get(c).getArea().get(j).getName();
                        city_AreaList.add(name);
                        province_AreaList.add(city_AreaList);//添加该省所有地区数据
                    }
                } catch (Exception e) {
                    Log.d("area_name",area_name);
                    e.printStackTrace();
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

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
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

            String province_id = options1Items.get(options1).getId();
            String city_id = options1Items.get(options1).getCity().get(options2).getId();
            String area_id = options1Items.get(options2).getCity().get(options2).getArea().get(options3).getId();
            String adCode = options1Items.get(options2).getCity().get(options2).getArea().get(options3).getAdCode();
            int districtLevel = options1Items.get(options2).getCity().get(options2).getArea().get(options3).getDistrictLevel();
            Map<String, Object> map = new HashMap<>();
            map.put("adCode", adCode);
            map.put("areaRegionId", area_id);
            map.put("cityRegionId", city_id);
            map.put("districtLevel", districtLevel);
            map.put("provinceRegionId", province_id);
            boolean sure_add = true;
            String tip = "";
            for(int i = 0; i < classAreaEntitys.size(); i++) {
                ClassAreaEntity classAreaEntity = classAreaEntitys.get(i);
                String city = classAreaEntity.getCity();
                String area = classAreaEntity.getRegion();
                if(city.equals(opt2tx)&& area.contains("全市")) {
                    sure_add = false;
                    tip = "你已添加"+opt2tx+"全市的上课区域，不能再次新增"+opt2tx+"的上课区域。";
                    break;
                }else {
                    sure_add = true;
                }
            }
            if(sure_add) {
                getPresenter().setAttendClassArea(map);
            }else {
                showAddFailDialog(tip);
            }
        })
                .setTitleText("")
                .setCancelColor(AppUtils.getColor(R.color.color_9A9A9A))
                .setSubmitColor(AppUtils.getColor(R.color.color_CD2A2A))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    @Override
    public void setAdapter(List<ClassAreaEntity> classAreaEntityList) {
        classAreaEntitys = classAreaEntityList;
        if(classAreaEntitys.size() == 0) {
            mTvAddClassArea.setText("添加上课区域");
        }else {
            mTvAddClassArea.setText("新增上课区域");
        }
        if (adapter == null) {
            adapter = new ClassAreaAdapter(classAreaEntityList);
            mRvClassArea.setAdapter(adapter);
        }else {
            adapter.setNewData(classAreaEntityList);
        }
    }

    @Override
    public void  addSuccess() {
        mSmartRel.autoRefresh();
    }

    @Override
    public void removeAreaItem(int position) {
        adapter.removeItem(position);
    }

    @Override
    public void finishRelsh() {
        mSmartRel.finishRefresh();
    }

    @SuppressLint("CheckResult")
    private void showAddFailDialog(String tip){
        new ConfigurableDialog(this)
                .setTextModel(ConfigurableDialog.TWO_TXT)
                .setTextFirst("新增失败")
                .setTextSecond(tip)
                .setBtnModel(ConfigurableDialog.ONE_BTN)
                .setBtnName(new String[]{"确认"})
                .setBtnListener((ConfigurableDialog, view) -> ConfigurableDialog.dismiss())
                .show();
    }
}
