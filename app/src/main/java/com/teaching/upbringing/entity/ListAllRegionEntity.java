package com.teaching.upbringing.entity;

import java.util.List;

/**
 * @author ChenHh
 * @time 2019/11/9 23:44
 * @des
 **/
public class ListAllRegionEntity {
    /**
     * districtLevel : 1
     * regionRsps : [{"adCode":"110000","districtLevel":1,"id":"1196062407315689473","name":"北京市"},{"adCode":"120000","districtLevel":1,"id":"1196062407315689509","name":"天津市"},{"adCode":"130000","districtLevel":1,"id":"1196062407315689545","name":"河北省"},{"adCode":"140000","districtLevel":1,"id":"1196062407324078125","name":"山西省"},{"adCode":"150000","districtLevel":1,"id":"1196062407328272403","name":"内蒙古自治区"},{"adCode":"210000","districtLevel":1,"id":"1196062407328272635","name":"辽宁省"},{"adCode":"220000","districtLevel":1,"id":"1196062407332466855","name":"吉林省"},{"adCode":"230000","districtLevel":1,"id":"1196062407332466995","name":"黑龙江省"},{"adCode":"310000","districtLevel":1,"id":"1196062407336661211","name":"上海市"},{"adCode":"320000","districtLevel":1,"id":"1196062407336661247","name":"江苏省"},{"adCode":"330000","districtLevel":1,"id":"1196062407340855301","name":"浙江省"},{"adCode":"340000","districtLevel":1,"id":"1196062407340855503","name":"安徽省"},{"adCode":"350000","districtLevel":1,"id":"1196062407340855747","name":"福建省"},{"adCode":"360000","districtLevel":1,"id":"1196062407340855937","name":"江西省"},{"adCode":"370000","districtLevel":1,"id":"1196062407345049699","name":"山东省"},{"adCode":"410000","districtLevel":1,"id":"1196062407345050007","name":"河南省"},{"adCode":"420000","districtLevel":1,"id":"1196062407345050359","name":"湖北省"},{"adCode":"430000","districtLevel":1,"id":"1196062407345050593","name":"湖南省"},{"adCode":"440000","districtLevel":1,"id":"1196062407345050867","name":"广东省"},{"adCode":"450000","districtLevel":1,"id":"1196062407349244079","name":"广西壮族自治区"},{"adCode":"460000","districtLevel":1,"id":"1196062407349244331","name":"海南省"},{"adCode":"500000","districtLevel":1,"id":"1196062407349244393","name":"重庆市"},{"adCode":"510000","districtLevel":1,"id":"1196062407349244475","name":"四川省"},{"adCode":"520000","districtLevel":1,"id":"1196062407349244885","name":"贵州省"},{"adCode":"530000","districtLevel":1,"id":"1196062407349245081","name":"云南省"},{"adCode":"540000","districtLevel":1,"id":"1196062407349245373","name":"西藏自治区"},{"adCode":"610000","districtLevel":1,"id":"1196062410289451079","name":"陕西省"},{"adCode":"620000","districtLevel":1,"id":"1196062410289451315","name":"甘肃省"},{"adCode":"630000","districtLevel":1,"id":"1196062410289451517","name":"青海省"},{"adCode":"640000","districtLevel":1,"id":"1196062410289451625","name":"宁夏回族自治区"},{"adCode":"650000","districtLevel":1,"id":"1196062410289451681","name":"新疆维吾尔自治区"},{"adCode":"710000","districtLevel":1,"id":"1196062410289451921","name":"台湾省"},{"adCode":"810000","districtLevel":1,"id":"1196062410289451923","name":"香港特别行政区"},{"adCode":"820000","districtLevel":1,"id":"1196062410289451961","name":"澳门特别行政区"},{"adCode":"900000","districtLevel":1,"id":"1196062410289451979","name":"外国"}]
     */

    private int districtLevel;
    private List<RegionRspsBean> regionRsps;

    public int getDistrictLevel() {
        return districtLevel;
    }

    public void setDistrictLevel(int districtLevel) {
        this.districtLevel = districtLevel;
    }

    public List<RegionRspsBean> getRegionRsps() {
        return regionRsps;
    }

    public void setRegionRsps(List<RegionRspsBean> regionRsps) {
        this.regionRsps = regionRsps;
    }

    public static class RegionRspsBean {
        /**
         * adCode : 110000
         * districtLevel : 1
         * id : 1196062407315689473
         * name : 北京市
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
