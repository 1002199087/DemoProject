package com.temporary.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.temporary.demoproject.R;

public class ArcView extends View {
    private int mWidth;
    private int mHeight;

    private int mArcHeight;
    private int mBgColor;
    private Context mContext;
    private Paint mPaint;

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttrs(context, attrs);
        mPaint = new Paint();
    }

    private void initAttrs(Context context, AttributeSet set) {
        TypedArray array = context.obtainStyledAttributes(set, R.styleable.arc_view_styleable);
        mArcHeight = array.getDimensionPixelSize(R.styleable.arc_view_styleable_arc_view_height, 0);
        mBgColor = array.getColor(R.styleable.arc_view_styleable_arc_view_bg_color,
                context.getResources().getColor(android.R.color.white));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBgColor);

        Rect rect = new Rect(0, 0, mWidth, mHeight - mArcHeight);
        canvas.drawRect(rect, mPaint);

        Path path = new Path();
        path.moveTo(0, mHeight - mArcHeight);
        path.quadTo(mWidth / 2, mHeight + mArcHeight, mWidth, mHeight - mArcHeight);
        canvas.drawPath(path, mPaint);
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
