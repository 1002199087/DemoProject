package com.temporary.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.temporary.demoproject.R;

@SuppressLint("AppCompatCustomView")
public class ArcPicView extends ImageView {
    private int mWidth;
    private int mHeight;

    private int mArcHeight;
    private Context mContext;

    public ArcPicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet set) {
        TypedArray array = context.obtainStyledAttributes(set, R.styleable.arc_view_styleable);
        mArcHeight = array.getDimensionPixelSize(R.styleable.arc_view_styleable_arc_view_height, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, mHeight - mArcHeight);
        path.quadTo(mWidth / 2, mHeight + mArcHeight, mWidth, mHeight - mArcHeight);
        path.lineTo(mWidth, 0);
        path.close();
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {// 表示父控件已经确切的指定了子查看的大小
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {// 表示父控件已经确切的指定了子查看的大小
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}
