package com.temporary.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.temporary.demoproject.R;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BitmapActivity extends AppCompatActivity {
    @BindView(R.id.bitmap_button)
    public Button mBitmapBtn;
    @BindView(R.id.bitmap_imageview)
    public ImageView mBitmapIV;
    private final String PATH = "/storage/emulated/0/com.econ.dh.cache/check/18M00000002001945" +
            "/scope_0.png_1529050962501_外施交流耐压试验_18M00000002001945.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bitmap_button)
    protected void getButton() {
        /*Bitmap bitmap = BitmapFactory.decodeFile(PATH);
        int newWidth = getResources().getDisplayMetrics().widthPixels;
        int newHeight = newWidth * bitmap.getHeight() / bitmap.getWidth();

        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth)/((float) bitmap.getWidth());
        float scaleHeight = ((float) newHeight)/((float) bitmap.getHeight());
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        mBitmapIV.setImageBitmap(newBitmap);*/

        //test();
    }

    /*private void test() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/storage/emulated/0/com.econ.dh.cache/check/18M00000002001945/test.xlsx");
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

            Log.e("wyy", "BitmapActivity test workbook.getNumberOfSheets = "+workbook.getNumberOfSheets());
        } catch (Exception e) {
            Log.e("wyy", "BitmapActivity test "+e.getMessage());
            e.printStackTrace();
        }
    }*/
}
