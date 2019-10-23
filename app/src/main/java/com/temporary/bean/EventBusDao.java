package com.temporary.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.temporary.demoproject.BR;

/**
 * Created by wyy on 2019/3/11 0011.
 */

public class EventBusDao extends BaseObservable {
    private String name;
    private int age;
    private String sex;

    public EventBusDao(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.temporary.demoproject.BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(com.temporary.demoproject.BR.age);
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(com.temporary.demoproject.BR.sex);
    }

    public void clone(EventBusDao dao) {
        setName(dao.getName());
        setAge(dao.getAge());
        setSex(dao.getSex());
    }
}
