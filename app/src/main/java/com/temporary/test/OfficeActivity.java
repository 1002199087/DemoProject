package com.temporary.test;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.temporary.demoproject.R;
import com.temporary.presenter.OfficePresenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfficeActivity extends AppCompatActivity {
    @BindView(R.id.read_button)
    protected Button mReadBtn;
    @BindView(R.id.read_xls_button)
    protected Button mReadXlsBtn;
    @BindView(R.id.read_doc_button)
    protected Button mReadDocBtn;
    private OfficePresenter mOfficePresenter;
    private static final String PATH = Environment.getExternalStorageDirectory()
            + "/com.econ.dh.cache/check/18M00000002001945/test.xlsx";
    private static final String PATH_XLS = Environment.getExternalStorageDirectory()
            + "/com.econ.dh.cache/check/18M00000002001945/scope_8.csv";// test2.xls
    private static final String PATH_DOC = Environment.getExternalStorageDirectory()
            + "/com.econ.dh.cache/check/18M00000002001945/test.doc";
    private static final String PATH_DOCX = Environment.getExternalStorageDirectory()
            + "/com.econ.dh.cache/check/18M00000002001945/Flashair设置.docx";
    private final String OFFICE_TYPE_XLS = "application/vnd.ms-excel";
    private final String OFFICE_TYPE_XLSX =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final String OFFICE_TYPE_DOC = "application/msword";
    private final String OFFICE_TYPE_DOCX =
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        ButterKnife.bind(this);

        mOfficePresenter = new OfficePresenter(this);
        mOfficePresenter.attach(this);
    }

    @OnClick({R.id.read_button, R.id.read_xls_button, R.id.read_doc_button, R.id.read_docx_button})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_button: {/*读取xlsx文档*/
                mOfficePresenter.readOfficeFile(PATH_XLS, OFFICE_TYPE_XLSX);
                break;
            }
            case R.id.read_xls_button: {/*读取xls文档*/
                mOfficePresenter.readOfficeFile(PATH_XLS, OFFICE_TYPE_XLS);
                break;
            }
            case R.id.read_doc_button: {/*读取doc文档*/
                mOfficePresenter.readOfficeFile(PATH_DOC, OFFICE_TYPE_DOC);
                break;
            }
            case R.id.read_docx_button: {/*读取docx文档*/
                /*mOfficePresenter.readOfficeFile(PATH_DOCX, OFFICE_TYPE_DOCX);*/
                //saveLog("sdfg");
                String string = null;
                int i = string.length();
                break;
            }
        }
    }

    private void saveLog(String log) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/测试log.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
            Date date = new Date(System.currentTimeMillis());
            String time = simpleDateFormat.format(date);
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string + "\r\n");
            }
            String content = stringBuffer.toString() + time +"\r\n"+ log;
            //File saveFile = new File(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    new FileOutputStream(file), "GBK");
            outputStreamWriter.append(content);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Exception e) {
            Log.e("wyy", "OfficeActivity saveLog " + e.getMessage());
            e.printStackTrace();
        }
    }
}
