package com.teaching.upbringing.entity;

public class UserInfoEntity {
    private String headImgUrl;
    private String introduce;
    private String nickname;
    private int sex;
    private long userId;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public UserInfoEntity setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public UserInfoEntity setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserInfoEntity setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public UserInfoEntity setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public UserInfoEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
