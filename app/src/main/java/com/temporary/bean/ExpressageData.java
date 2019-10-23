package com.temporary.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Dee on 2018/9/3 0003.
 */

@Entity
public class ExpressageData {
    @Id(autoincrement = true)
    private Long _id;
    private String time;
    private String ftime;
    private String context;
    private String location;

    public ExpressageData(String time, String ftime, String context, String location) {
        this.time = time;
        this.ftime = ftime;
        this.context = context;
        this.location = location;
    }

    @Generated(hash = 1400485761)
    public ExpressageData(Long _id, String time, String ftime, String context,
            String location) {
        this._id = _id;
        this.time = time;
        this.ftime = ftime;
        this.context = context;
        this.location = location;
    }

    @Generated(hash = 1804595600)
    public ExpressageData() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
