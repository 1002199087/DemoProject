package com.temporary.demoproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.temporary.bean.SignatureTokenResponseDao;
import com.temporary.custom.SignatureView;
import com.temporary.network.impl.SignatureRequestImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignatureActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.clear_button)
    Button mClearBtn;
    @BindView(R.id.signature_view)
    SignatureView mSignatureView;

    private SimpleDateFormat mSimpleDateFormat;

    private QMUITipDialog qmuiTipDialog;

    private SignatureRequestImpl mSignatureRequestImpl;

    private final int FLAG_SIGNATURE_TOKEN = 1;
    private final int FLAG_SIGNATURE_BITMAP = 2;

    private String mBmpString = "";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FLAG_SIGNATURE_TOKEN: {
                    if (msg.obj != null && msg.obj instanceof SignatureTokenResponseDao) {
                        SignatureTokenResponseDao dao = (SignatureTokenResponseDao) msg.obj;
                        Log.e("wyy", "SignatureActivity handleMessage " + dao.getAccess_token());
                        mSignatureRequestImpl.requestSignatureString(dao.getAccess_token(),
                                mBmpString, mHandler, FLAG_SIGNATURE_BITMAP);
                    }
                    break;
                }
                case FLAG_SIGNATURE_BITMAP: {
                    break;
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        ButterKnife.bind(this);

        initTopbar();
        mSimpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

        mSignatureRequestImpl = new SignatureRequestImpl();

        mClearBtn.setElevation(10);
        mClearBtn.setTranslationZ(10);
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.electronic_signature));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.clear_button})
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_button: {
                Bitmap bitmap = loadBitmapForView(mSignatureView);
                mBmpString = bmpToSring(bitmap);
                Log.e("wyy", "SignatureActivity onViewClicked " + mBmpString);
                //new SignatureBmpTask().execute(bitmap);

                mSignatureRequestImpl.requestSignatureToken("client_credentials",
                        "p7GMh7Hml2EgW9v5Sc9gtzlE", "Nu8k3dACd4XcQE4VZ6xhPRGQ5foNcN6r", mHandler,
                        FLAG_SIGNATURE_TOKEN);

                mSignatureView.clear();
                break;
            }
        }
    }

    private class SignatureBmpTask extends AsyncTask<Bitmap, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {
            return saveBmp(bitmaps[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                qmuiTipDialog = new QMUITipDialog.Builder(SignatureActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord(getString(R
                                .string.save_success)).create();
            } else {
                qmuiTipDialog = new QMUITipDialog.Builder(SignatureActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord(getString(R
                                .string.save_fail)).create();
            }
            qmuiTipDialog.show();
            mSignatureView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    qmuiTipDialog.dismiss();
                }
            }, 2000);
        }
    }

    private boolean saveBmp(Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;

        try {
            File fileFolder = new File(Environment.getExternalStorageDirectory() + "/signature");
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            File file = new File(fileFolder.getPath() + "/" + mSimpleDateFormat
                    .format(new Date(System.currentTimeMillis())) + ".png");
            fileOutputStream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private Bitmap loadBitmapForView(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        int w = view.getWidth();
        int h = view.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        canvas.drawColor(Color.WHITE);

        //view.layout(0, 0, w, h);
        view.draw(canvas);
        view.destroyDrawingCache();
        return bmp;
    }

    private String bmpToSring(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
