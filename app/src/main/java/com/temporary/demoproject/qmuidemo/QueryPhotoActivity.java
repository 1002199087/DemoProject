package com.temporary.demoproject.qmuidemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryPhotoActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.select_photo_button)
    QMUIRoundButton mSelectPhotoBtn;
    @BindView(R.id.query_photo_imageview)
    ImageView mQueryPhotoIV;
    @BindView(R.id.take_photp_button)
    QMUIRoundButton mTakePhotpBtn;

    private final int QUERY_PHOTO_RESQUEST_CODE = 0;
    private final int TAKE_PHOTO_RESQUEST_CODE = 1;
    private String mTakePhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_photo);
        ButterKnife.bind(this);

        checkPermission();

        initTopbar();
    }

    private void checkPermission() {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }*/
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.select_photp_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.select_photo_button, R.id.take_photp_button})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.select_photo_button: {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, QUERY_PHOTO_RESQUEST_CODE);
                break;
            }
            case R.id.take_photp_button: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String name = System.currentTimeMillis() + ".jpg";
                File saveFile = new File(
                        Environment.getExternalStorageDirectory().getPath()
                                + "/demoSaveFile");
                if (!saveFile.exists()) {
                    saveFile.mkdirs();
                }
                mTakePhotoPath = saveFile.getPath() + "/" + name;
                Uri uri = FileProvider.getUriForFile(this,
                        "org.unreal.update.demo",
                        new File(mTakePhotoPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, TAKE_PHOTO_RESQUEST_CODE);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deletePhotos(Environment.getExternalStorageDirectory() + "/demoSaveFile");
    }

    /**
     * 删除文件夹
     *
     * @param path 文件夹路径
     */
    private void deletePhotos(String path) {
        File fileRoot = new File(path);
        if (fileRoot.exists()) {
            if (fileRoot.isDirectory()) {
                File[] files = fileRoot.listFiles();
                for (File f : files) {
                    deletePhotos(f.getPath());
                }
                fileRoot.delete();
            } else {
                fileRoot.delete();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QUERY_PHOTO_RESQUEST_CODE && data != null && data.getData() != null) {
            String pathBitmap = getPathFromURI(data.getData());
            Bitmap bitmap = BitmapFactory.decodeFile(pathBitmap);
            mQueryPhotoIV.setImageBitmap(bitmap);
        } else if (requestCode == TAKE_PHOTO_RESQUEST_CODE) {
            try {
                InputStream inputStream = new FileInputStream(mTakePhotoPath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mQueryPhotoIV.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * uri 转 path
     *
     * @param uri
     * @return
     */
    private String getPathFromURI(Uri uri) {
        String[] projects = {MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery(uri, projects, null, null, null);
        int i = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(i);
    }
}
