package com.teaching.upbringing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IdRes;

public class PersonerFuncWrapper implements Parcelable {

    @IdRes
    private int mFuncResId;

    private String mFuncName;

    private boolean isVisibleRedDot;

    private int redDotNum;

    private boolean mEnable;

    private boolean isEmpty;//是否一个占位符而已

    public PersonerFuncWrapper() {
    }

    public PersonerFuncWrapper(int funcResId, String funcName) {
        mFuncResId = funcResId;
        mFuncName = funcName;
    }

    public PersonerFuncWrapper(boolean is) {
        isEmpty = is;
    }


    public boolean isVisibleRedDot() {
        return isVisibleRedDot;
    }

    public void setVisibleRedDot(boolean visibleRedDot) {
        isVisibleRedDot = visibleRedDot;
    }

    public int getRedDotNum() {
        return redDotNum;
    }

    public void setRedDotNum(int redDotNum) {
        this.redDotNum = redDotNum;
    }

    public int getFuncResId() {
        return mFuncResId;
    }

    public String getFuncName() {
        return mFuncName;
    }

    public boolean isEnable() {
        return mEnable;
    }

    public void setFuncResId(int mFuncResId) {
        this.mFuncResId = mFuncResId;
    }

    public void setFuncName(String mFuncName) {
        this.mFuncName = mFuncName;
    }

    public void setEnable(boolean enable) {
        mEnable = enable;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mFuncResId);
        dest.writeString(this.mFuncName);
        dest.writeByte(this.isVisibleRedDot ? (byte) 1 : (byte) 0);
        dest.writeInt(this.redDotNum);
        dest.writeByte(this.mEnable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEmpty ? (byte) 1 : (byte) 0);
    }

    protected PersonerFuncWrapper(Parcel in) {
        this.mFuncResId = in.readInt();
        this.mFuncName = in.readString();
        this.isVisibleRedDot = in.readByte() != 0;
        this.redDotNum = in.readInt();
        this.mEnable = in.readByte() != 0;
        this.isEmpty = in.readByte() != 0;
    }
    
    public static final Creator<PersonerFuncWrapper> CREATOR = new Creator<PersonerFuncWrapper>() {
        @Override
        public PersonerFuncWrapper createFromParcel(Parcel source) {
            return new PersonerFuncWrapper(source);
        }
        
        @Override
        public PersonerFuncWrapper[] newArray(int size) {
            return new PersonerFuncWrapper[size];
        }
    };
    
    @Override
    public String toString() {
        return "HomeFuncWrapper{" +
                "mFuncResId=" + mFuncResId +
                ", mFuncName='" + mFuncName + '\'' +
                ", isVisibleRedDot=" + isVisibleRedDot +
                ", redDotNum=" + redDotNum +
                ", mEnable=" + mEnable +
                ", isEmpty=" + isEmpty +
                '}';
    }
}
