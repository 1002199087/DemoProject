package com.temporary.custom;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.temporary.util.BlurBitmapUtil;

/**
 * Created by Administrator on 2019/3/19 0019.
 */

public class GlideBlurformation extends BitmapTransformation {
    private Context mContext;

    public GlideBlurformation(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return BlurBitmapUtil.instance().blurBitmap(mContext, toTransform, 20, outWidth, outHeight);
    }

    @Override
    public String getId() {
        return null;
    }
}
