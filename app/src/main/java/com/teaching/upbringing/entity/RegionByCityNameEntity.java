package com.teaching.upbringing.entity;

import java.util.List;

/**
 * @author ChenHh
 * @time 2019/11/11 0:45
 * @des
 **/
public class RegionByCityNameEntity {
    private String letter;
    private List<RegionRspsBean> regionRsps;

    public String getLetter() {
        return letter;
    }

    public RegionByCityNameEntity setLetter(String letter) {
        this.letter = letter;
        return this;
    }

    public List<RegionRspsBean> getRegionRsps() {
        return regionRsps;
    }

    public RegionByCityNameEntity setRegionRsps(List<RegionRspsBean> regionRsps) {
        this.regionRsps = regionRsps;
        return this;
    }

    public class RegionRspsBean{
        private String adCode;
        private int districtLevel;
        private String id;
        private String name;

        public String getAdCode() {
            return adCode;
        }

        public RegionRspsBean setAdCode(String adCode) {
            this.adCode = adCode;
            return this;
        }

        public int getDistrictLevel() {
            return districtLevel;
        }

        public RegionRspsBean setDistrictLevel(int districtLevel) {
            this.districtLevel = districtLevel;
            return this;
        }

        public String getId() {
            return id;
        }

        public RegionRspsBean setId(String id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public RegionRspsBean setName(String name) {
            this.name = name;
            return this;
        }
    }
}
