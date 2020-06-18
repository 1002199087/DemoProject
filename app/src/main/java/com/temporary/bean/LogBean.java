package com.temporary.bean;

/**
 * theme:
 * author：wyy
 */
public class LogBean {

    private String info;

    private String keyString;

    private MeterDataBean bean;

    private int mode;// 0:普通文字 1:错误提示 2：表号 3：蓝牙地址

    public LogBean(Builder builder) {
        this.info = builder.info;
        this.keyString = builder.keyString;
        this.bean = builder.bean;
        this.mode = builder.mode;
    }

    public static class Builder {
        String info;
        String keyString;
        MeterDataBean bean;
        int mode;

        public Builder info(String info) {
            this.info = info;
            return this;
        }

        public Builder keyString(String keyString) {
            this.keyString = keyString;
            return this;
        }

        public Builder meterDataBean(MeterDataBean bean) {
            this.bean = bean;
            return this;
        }

        public Builder mode(int mode) {
            this.mode = mode;
            return this;
        }

        public LogBean build() {
            LogBean bean = new LogBean(this);
            return bean;
        }
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public MeterDataBean getBean() {
        return bean;
    }

    public void setBean(MeterDataBean bean) {
        this.bean = bean;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
