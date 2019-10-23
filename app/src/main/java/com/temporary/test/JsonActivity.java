package com.temporary.test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.BaseActivity;
import com.temporary.demoproject.R;
import com.temporary.demoproject.viewmodel.IJsonView;
import com.temporary.presenter.JsonPresenter;
import com.temporary.util.MaintenancePlan;
import com.temporary.util.PlanVO;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class JsonActivity extends BaseActivity<IJsonView, JsonPresenter> implements IJsonView {
    @BindView(R.id.test_button)
    protected Button mTestBtn;
    @BindView(R.id.get_detail_button)
    protected Button mGetDetailBtn;
    @BindView(R.id.upload_button)
    protected Button mUploadBtn;
    @BindView(R.id.result_textview)
    protected TextView mResultTV;
    private final String JSON_STRING = "{\"data\":{\"fCity\":\"省直属\",\"fCityId\":\"jxjx-jx\",\"fDevicename\":\"5021开关间隔HGIS设备组合电器\",\"fDevicetypecode\":\"0321\",\"fSimplename\":\"石钟山变\",\"fSubstaionId\":\"D0B7EF21-D97C-4453-AD34-D7B330DA379D-00011\",\"planVO\":{\"fDescription\":\"备注\",\"fDevicecode\":\"18M00000002001945\",\"fExecuteperson\":\"执行者1\",\"fHashCode\":\"1000001181374163\",\"fMaintainproject\":\"外施交流耐压试验\",\"fMaintainprojectid\":\"ACWithstand\",\"fMaintaintype\":\"出厂\",\"fMaintaintypeid\":\"leaveFactory\",\"fOrderid\":\"18M00000002001945_ACWithstand_1507773071183\",\"fScheduletime\":1507773071183,\"fStatus\":\"HAVEINHAND\"}},\"pageNo\":0,\"status\":0,\"statusText\":\"ok\",\"totalCount\":0}";
    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.baidumap_button)
    Button mBaidumapBtn;
    @BindView(R.id.gaodemap_button)
    Button mGaodemapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.retrofit_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initTopbar() {

    }

    private void checkPermission() {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }*/
    }

    @OnClick({R.id.test_button, R.id.get_detail_button, R.id.upload_button, R.id.baidumap_button,
            R.id.gaodemap_button})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_button: {//登陆请求 admin 123456
                mPresenter.login("admin", "123456");
                break;
            }
            case R.id.get_detail_button: {//结果查询
                mPresenter.checkDetail("18M00000002009712_VHFPartial_1530858090461");
                break;
            }
            case R.id.upload_button: {//上传文件
                File file = new File(Environment.getExternalStorageDirectory()
                        + "/1530758228944_外施交流耐压试验_18M00000002009711.txt");
                String json = "{\"bluetoothName\":\"BLE Device-1785A5\",\"checkItem\":\"VHFPartial\"," +
                        "\"detectTime\":1530866334224,\"devId\":\"18M00000002009711\"," +
                        "\"fExecuteperson\":\"13429119827\"," +
                        "\"fHashCode\":\"1000000309664471\"," +
                        "\"fOrderid\":\"18M00000002009711_VHFPartial_1530866334224\"," +
                        "\"storeLocation\":\"/storage/emulated/0/com.econ.dh.cache/check/18M00000002009711\"}";
                mPresenter.uploadFile(file, json);
                break;
            }
            case R.id.baidumap_button: {
                Intent intent = new Intent();
                //intent.setData(Uri.parse("baidumap://map/direction?origin=name:对外经贸大学|latlng:39.98871,116.43234&destination=西直门&mode=transit&sy=3&index=10&target=1&src=andr.baidu.openAPIdemo"));
                intent.setData(Uri.parse("baidumap://map/direction?origin=杭州紫金广场&destination=杭州火车东站&mode=transit&sy=3&index=10&target=1&src=andr.baidu.openAPIdemo"));
                startActivity(intent);
                break;
            }
            case R.id.gaodemap_button: {
                Intent intent = new Intent();
                //androidamap://route?sourceApplication=%1$s&slat=%2$s&slon=%3$s&sname=%4$s&dlat=%5$s&dlon=%6$s&dname=%7$s&dev=0&m=0&t=2"
                intent.setData(Uri.parse("androidamap://route?sourceApplication=DemoProject&sname=杭州紫金广场&dname=杭州火车东站&dev=0&m=0&t=2"));
                startActivity(intent);
                break;
            }
        }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_json;
    }

    @Override
    protected JsonPresenter getPresenter() {
        return new JsonPresenter(this);
    }

    private MaintenancePlan jsonToMaintenancePlan(String json) {
        try {
            MaintenancePlan maintenancePlan = new MaintenancePlan();
            JSONObject jsonObject = new JSONObject(json);
            String dataString = jsonObject.optString("data");
            JSONObject dataJson = new JSONObject(dataString);
            maintenancePlan.setfCity(dataJson.optString("fCity"));
            maintenancePlan.setfCityId(dataJson.optString("fCityId"));
            maintenancePlan.setfDevicename(dataJson.optString("fDevicename"));
            maintenancePlan.setfDevicetypecode(dataJson.optString("fDevicetypecode"));
            maintenancePlan.setfSimplename(dataJson.optString("fSimplename"));
            maintenancePlan.setfSubstaionId(dataJson.optString("fSubstaionId"));

            String planVoString = dataJson.optString("planVO");
            JSONObject planVoJson = new JSONObject(planVoString);
            PlanVO planVO = new PlanVO();
            planVO.setfDescription(planVoJson.optString("fDescription"));
            planVO.setfDevicecode(planVoJson.optString("fDevicecode"));
            planVO.setfExecuteperson(planVoJson.optString("fExecuteperson"));
            planVO.setfHashCode(planVoJson.optString("fHashCode"));
            planVO.setfMaintainproject(planVoJson.optString("fMaintainproject"));
            planVO.setfMaintainprojectid(planVoJson.optString("fMaintainprojectid"));
            planVO.setfMaintaintype(planVoJson.optString("fMaintaintype"));
            planVO.setfMaintaintypeid(planVoJson.optString("fMaintaintypeid"));
            planVO.setfOrderid(planVoJson.optString("fOrderid"));
            planVO.setfScheduletime(planVoJson.optLong("fScheduletime"));
            planVO.setfStatus(planVoJson.optString("fStatus"));
            maintenancePlan.setPlanVO(planVO);
            Log.e("wyy", "JsonActivity jsonToMaintenancePlan " + maintenancePlan.getPlanVO().getfScheduletime());
            return maintenancePlan;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void refreshResult(String string) {
        mResultTV.setText(string);
    }
}
