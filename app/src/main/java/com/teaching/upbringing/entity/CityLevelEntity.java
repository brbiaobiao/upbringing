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

    private String id;
    private String name;
    private String adCode;
    private int districtLevel;
    private List<CityBean> city;

    public String getId() {
        return id;
    }

    public CityLevelEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityLevelEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getAdCode() {
        return adCode;
    }

    public CityLevelEntity setAdCode(String adCode) {
        this.adCode = adCode;
        return this;
    }

    public int getDistrictLevel() {
        return districtLevel;
    }

    public CityLevelEntity setDistrictLevel(int districtLevel) {
        this.districtLevel = districtLevel;
        return this;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
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
