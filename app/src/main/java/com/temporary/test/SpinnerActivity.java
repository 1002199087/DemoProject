package com.temporary.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.temporary.demoproject.R;
import com.temporary.util.MySpinner;

public class SpinnerActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private MySpinner mSpinner;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        init();
    }

    private void init() {
        mSpinner = (MySpinner) findViewById(R.id.demo_spinner);

        mAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_spinner_item, R.id.spinner_item_textview);
        /*mAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, R.layout.activity_spinner_item);*/
        mAdapter.setDropDownViewResource(R.layout.activity_spinner_item_down);
        mAdapter.add("请选择检测项目");
        mAdapter.add(getResources().getString(R.string.bluetooth_devices));

        mAdapter.add(getResources().getString(R.string.wifi_sd_devices));
        mSpinner.setAdapter(mAdapter);
        mSpinner.setPrompt("test");
        mSpinner.setSelection(0, true);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("wyy", "SpinnerActivity onItemSelected i = "
                + i + " , adapterView = " + adapterView);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
