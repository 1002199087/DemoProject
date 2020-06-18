package com.temporary.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MeterDataBean implements Parcelable {
    String userNumber = "";//用户编号
    String meterChannelNo = "";//通道号
    String meterNumber = "";//表号
    String meterCpu = "";//cpu
    String meterOem = "";//厂商
    String userName = "";//用户名称
    String userAddress = "";//用户地址
    String isCheck = "0";//是否抄表//0表示未抄表，1表示已抄表
    String meterCurrentReading = "0";//当前读数
    String meterbeforeReading = "0";//上次读数
    String meterState = "";//表状态
    String meterName = "";//表名
    String temp1 = "";//预留1
    String temp2 = "";//预留1
    String temp3 = "";//预留1
    String temp4 = "";//预留1
    String temp5 = "";//预留1
    String temp6 = "";//预留1
    String temp7 = "";//预留1
    String temp8 = "";//预留1
    String readTime = "";// 抄表时间
    public static String USERNUMBER = "userNumber";
    public static String METERCHANNELNO = "meterChannelNo";
    public static String METERNUMBER = "meterNumber";
    public static String METERNAME = "meterName";
    public static String METERCPU = "meterCpu";
    public static String METEROEM = "meterOem";
    public static String USERNAME = "userName";
    public static String USERADDRESS = "userAddress";
    public static String METERISCHECK = "isCheck";
    public static String METERCURRENTREADING = "meterCurrentReading";
    public static String METERBEFOREREADING = "meterbeforeReading";
    public static String METERSTATE = "meterState";
    public static String TEMP1 = "temp1";
    public static String TEMP2 = "temp2";
    public static String TEMP3 = "temp3";
    public static String TEMP4 = "temp4";
    public static String TEMP5 = "temp5";
    public static String TEMP6 = "temp6";
    public static String TEMP7 = "temp7";
    public static String TEMP8 = "temp8";
    public static String READTIME = "readTime";

    public MeterDataBean() {

    }

    protected MeterDataBean(Parcel in) {
        userNumber = in.readString();
        meterChannelNo = in.readString();
        meterNumber = in.readString();
        meterCpu = in.readString();
        meterOem = in.readString();
        userName = in.readString();
        userAddress = in.readString();
        isCheck = in.readString();
        meterCurrentReading = in.readString();
        meterbeforeReading = in.readString();
        meterState = in.readString();
        meterName = in.readString();
        temp1 = in.readString();
        temp2 = in.readString();
        temp3 = in.readString();
        temp4 = in.readString();
        temp5 = in.readString();
        temp6 = in.readString();
        temp7 = in.readString();
        temp8 = in.readString();
        readTime = in.readString();
    }

    public static final Creator<MeterDataBean> CREATOR = new Creator<MeterDataBean>() {
        @Override
        public MeterDataBean createFromParcel(Parcel in) {
            return new MeterDataBean(in);
        }

        @Override
        public MeterDataBean[] newArray(int size) {
            return new MeterDataBean[size];
        }
    };

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    public String getTemp5() {
        return temp5;
    }

    public void setTemp5(String temp5) {
        this.temp5 = temp5;
    }

    public String getTemp6() {
        return temp6;
    }

    public void setTemp6(String temp6) {
        this.temp6 = temp6;
    }

    public String getTemp7() {
        return temp7;
    }

    public void setTemp7(String temp7) {
        this.temp7 = temp7;
    }

    public String getTemp8() {
        return temp8;
    }

    public void setTemp8(String temp8) {
        this.temp8 = temp8;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getMeterChannelNo() {
        return meterChannelNo;
    }

    public void setMeterChannelNo(String meterChannelNo) {
        this.meterChannelNo = meterChannelNo;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMeterCpu() {
        return meterCpu;
    }

    public void setMeterCpu(String meterCpu) {
        this.meterCpu = meterCpu;
    }

    public String getMeterOem() {
        return meterOem;
    }

    public void setMeterOem(String meterOem) {
        this.meterOem = meterOem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getMeterCurrentReading() {
        return meterCurrentReading;
    }

    public void setMeterCurrentReading(String meterCurrentReading) {
        this.meterCurrentReading = meterCurrentReading;
    }

    public String getMeterBeforeReading() {
        return meterbeforeReading;
    }

    public void setMeterBeforeReading(String meterbeforeReading) {
        this.meterbeforeReading = meterbeforeReading;
    }

    public String getMeterState() {
        return meterState;
    }

    public void setMeterState(String meterState) {
        this.meterState = meterState;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userNumber);
        parcel.writeString(meterChannelNo);
        parcel.writeString(meterNumber);
        parcel.writeString(meterCpu);
        parcel.writeString(meterOem);
        parcel.writeString(userName);
        parcel.writeString(userAddress);
        parcel.writeString(isCheck);
        parcel.writeString(meterCurrentReading);
        parcel.writeString(meterbeforeReading);
        parcel.writeString(meterState);
        parcel.writeString(meterName);
        parcel.writeString(temp1);
        parcel.writeString(temp2);
        parcel.writeString(temp3);
        parcel.writeString(temp4);
        parcel.writeString(temp5);
        parcel.writeString(temp6);
        parcel.writeString(temp7);
        parcel.writeString(temp8);
        parcel.writeString(readTime);
    }
}
