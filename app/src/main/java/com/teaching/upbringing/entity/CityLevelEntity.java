package com.teaching.upbringing.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author bb
 * @time 2019/11/18 15:56
 * @des ${TODO}
 **/
public class CityLevelEntity implements IPickerViewData {

    /**
     * province_id : 1196062407345050867
     * province_name : 广东省
     * province_adCode : 440000
     * province_districtLevel : 1
     * city : [{"adCode":"440100","districtLevel":2,"id":"1196062407345050869","name":"广州市","area":[{"adCode":"440103","districtLevel":3,"id":"1196062407345050871","name":"荔湾区"},{"adCode":"440104","districtLevel":3,"id":"1196062407345050873","name":"越秀区"}]},{"adCode":"440200","districtLevel":2,"id":"1196062407345050893","name":"韶关市","area":[{"adCode":"440103","districtLevel":3,"id":"1196062407345050871","name":"荔湾区"},{"adCode":"440104","districtLevel":3,"id":"1196062407345050873","name":"越秀区"}]}]
     */

    private String province_id;
    private String province_name;
    private String province_adCode;
    private int province_districtLevel;
    private List<CityBean> city;

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getProvince_adCode() {
        return province_adCode;
    }

    public void setProvince_adCode(String province_adCode) {
        this.province_adCode = province_adCode;
    }

    public int getProvince_districtLevel() {
        return province_districtLevel;
    }

    public void setProvince_districtLevel(int province_districtLevel) {
        this.province_districtLevel = province_districtLevel;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    @Override
    public String getPickerViewText() {
        return this.province_name;
    }

    public static class CityBean {
        /**
         * adCode : 440100
         * districtLevel : 2
         * id : 1196062407345050869
         * name : 广州市
         * area : [{"adCode":"440103","districtLevel":3,"id":"1196062407345050871","name":"荔湾区"},{"adCode":"440104","districtLevel":3,"id":"1196062407345050873","name":"越秀区"}]
         */

        private String adCode;
        private int districtLevel;
        private String id;
        private String name;
        private List<AreaBean> area;

        public String getAdCode() {
            return adCode;
        }

        public void setAdCode(String adCode) {
            this.adCode = adCode;
        }

        public int getDistrictLevel() {
            return districtLevel;
        }

        public void setDistrictLevel(int districtLevel) {
            this.districtLevel = districtLevel;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {
            /**
             * adCode : 440103
             * districtLevel : 3
             * id : 1196062407345050871
             * name : 荔湾区
             */

            private String adCode;
            private int districtLevel;
            private String id;
            private String name;

            public String getAdCode() {
                return adCode;
            }

            public void setAdCode(String adCode) {
                this.adCode = adCode;
            }

            public int getDistrictLevel() {
                return districtLevel;
            }

            public void setDistrictLevel(int districtLevel) {
                this.districtLevel = districtLevel;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
