package com.teaching.upbringing.entity;

import java.util.List;

/**
 * @author bb
 * @time 2019/11/18 15:56
 * @des ${TODO}
 **/
public class CityLevelEntity {

    /**
     * province_name : 广东省
     * province_id :
     * city : [{"city_name":"广州市","city_id":"","area":[{"area_name":"越秀区","area_id":""}]}]
     */

    private String province_name;
    private String province_id;
    private List<CityBean> city;

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * city_name : 广州市
         * city_id :
         * area : [{"area_name":"越秀区","area_id":""}]
         */

        private String city_name;
        private String city_id;
        private List<AreaBean> area;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {
            /**
             * area_name : 越秀区
             * area_id :
             */

            private String area_name;
            private String area_id;

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }
        }
    }
}
