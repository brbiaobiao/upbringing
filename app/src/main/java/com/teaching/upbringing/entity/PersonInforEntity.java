package com.teaching.upbringing.entity;

/**
 * @author bb
 * @time 2019/11/1 16:38
 * @des ${TODO}
 **/
public class PersonInforEntity {

    private String brightSpot;
    private long createdAt;
    private String headImgUrl;
    private boolean ifTeacher;
    private String introduce;
    private String nickname;
    private int sex;
    private String title;
    private long userId;

    public String getBrightSpot() {
        return brightSpot;
    }

    public PersonInforEntity setBrightSpot(String brightSpot) {
        this.brightSpot = brightSpot;
        return this;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public PersonInforEntity setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public PersonInforEntity setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
        return this;
    }

    public boolean isIfTeacher() {
        return ifTeacher;
    }

    public PersonInforEntity setIfTeacher(boolean ifTeacher) {
        this.ifTeacher = ifTeacher;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public PersonInforEntity setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public PersonInforEntity setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public PersonInforEntity setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PersonInforEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public PersonInforEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    private class AttendClassAre{
        private String city;
        private int districtLevel;
        private String province;
        private String region;

        public String getCity() {
            return city;
        }

        public AttendClassAre setCity(String city) {
            this.city = city;
            return this;
        }

        public int getDistrictLevel() {
            return districtLevel;
        }

        public AttendClassAre setDistrictLevel(int districtLevel) {
            this.districtLevel = districtLevel;
            return this;
        }

        public String getProvince() {
            return province;
        }

        public AttendClassAre setProvince(String province) {
            this.province = province;
            return this;
        }

        public String getRegion() {
            return region;
        }

        public AttendClassAre setRegion(String region) {
            this.region = region;
            return this;
        }
    }

}
