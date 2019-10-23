package com.temporary.util;

/**
 * Created by wyy on 2019/3/6 0006.
 */

public class DataBindingUtil {
    public static String getItemCountDesc(int itemCount) {
        return itemCount + " 条项目";
    }

    public static String getEventBusDaoInfo(String name, int age, String sex) {
        return name + " " + age + " " + sex;
    }

    public static String getEventBusDaoInfo(String name) {
        return name;
    }
}
