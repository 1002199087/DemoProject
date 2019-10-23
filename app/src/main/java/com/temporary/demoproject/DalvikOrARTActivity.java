package com.temporary.demoproject;

import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.temporary.demoproject.databinding.ActivityDalvikOrArtBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DalvikOrARTActivity extends SimpleBaseActivity<ActivityDalvikOrArtBinding> {
    public ObservableField<String> mDalOrARTString = new ObservableField<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_dalvik_or_art;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string.art_title));
        mDataBinding.mQmuiTopbar.addLeftBackImageButton().setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        mDataBinding.setActivity(this);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mOneBtn: {// 方法一
                String vmVer = System.getProperty("java.vm.version");
                mDalOrARTString.set(vmVer + " \n(如果使用的是ART虚拟机的话，属性值会大于等于2.0.0)");
                break;
            }
            case R.id.mTwoBtn: {// 方法二
                try {
                    Class<?> systemProperties = Class.forName("android.os.SystemProperties");
                    Method method = systemProperties.getMethod("get", String.class, String.class);
                    String value = (String) method.invoke(systemProperties, "persist.sys.dalvik" +
                            ".vm" +
                            ".lib", "Dalvik");
                    mDalOrARTString.set(value);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
