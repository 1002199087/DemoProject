package com.temporary.bean;

import java.util.List;

/**
 * Created by Dee on 2018/9/4 0004.
 */

public class ExpressageNetDao {
    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<ExpressageData> data;

    public ExpressageNetDao(String message, String nu, String ischeck, String condition,
                            String com, String status, String state, List<ExpressageData> data) {
        this.message = message;
        this.nu = nu;
        this.ischeck = ischeck;
        this.condition = condition;
        this.com = com;
        this.status = status;
        this.state = state;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ExpressageData> getData() {
        return data;
    }

    public void setData(List<ExpressageData> data) {
        this.data = data;
    }
}
