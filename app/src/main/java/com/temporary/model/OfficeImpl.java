package com.temporary.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;


/**
 * Created by Dee on 2018/6/20.
 */

public class OfficeImpl implements IOfficeModel {

    @Override
    public void readOfficeFile(Context context, String path, String type) {
        try {
            File file = new File(path);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(context, "org.unreal.update.demo", file);
            intent.setDataAndType(uri, type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("wyy", "OfficeImpl readXlsxFile " + e.getMessage());
            e.printStackTrace();
        }
    }

}
