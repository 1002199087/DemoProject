package com.temporary.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dee on 2018/7/19.
 */

public class PlanetView extends View implements Runnable {
    private Context mContext;
    private float mCenterX;
    private float mCenterY;
    private float mRadius = 100;
    private Paint mStrokePaint;
    private Paint mFillPaint;
    private int mPlanetCount = 0;
    private List<Integer> mExtraSpeed = new ArrayList<>();

    public PlanetView(Context context) {
        super(context);
    }

    public PlanetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        mStrokePaint = new Paint();
        mStrokePaint.setColor(Color.BLACK);//画笔颜色
        mStrokePaint.setStyle(Paint.Style.STROKE);//画笔空心
        mStrokePaint.setStrokeWidth(10f);
        mStrokePaint.setAntiAlias(true);

        mFillPaint = new Paint();
        mFillPaint.setColor(Color.BLACK);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setStrokeWidth(20f);
        mFillPaint.setAntiAlias(true);
        mExtraSpeed.clear();
        for (int i = 0; i < 4; i++) {
            mExtraSpeed.add((int) (Math.random() * 100));
        }
    }

    public PlanetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlanetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float r = 50f;
        float rate = 1.6f;
        mStrokePaint.setStrokeWidth(10f);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mStrokePaint);
        for (int i = 1; i < 5; i++) {
            mStrokePaint.setStrokeWidth(10f / (i + 0.5f));
            canvas.drawCircle(mCenterX, mCenterY, (float) (i * mRadius + r * rate), mStrokePaint);
            r = r * rate;
        }

        r = 50f;
        float planetX = (float) (mCenterX + mRadius * Math.cos(mPlanetCount % 360 * Math.PI / 180));
        float planetY = (float) (mCenterY + mRadius * Math.sin(mPlanetCount % 360 * Math.PI / 180));
        canvas.drawCircle(planetX, planetY, 10f, mFillPaint);
        for (int i = 1; i < 5; i++) {
            int flag = i % 2 == 0 ? 1 : -1;
            planetX = (float) (mCenterX + (float) (i * mRadius + r * rate)
                    * Math.cos(flag * (mPlanetCount + mExtraSpeed.get(i - 1)) % 360 * Math.PI / 180));
            planetY = (float) (mCenterY + (float) (i * mRadius + r * rate)
                    * Math.sin(flag * (mPlanetCount + mExtraSpeed.get(i - 1)) % 360 * Math.PI / 180));
            r = r * rate;
            canvas.drawCircle(planetX, planetY, 10f, mFillPaint);
        }
        mPlanetCount++;
        postDelayed(this, 10);
    }

    @Override
    public void run() {
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = (float) measureWidth(widthMeasureSpec) / 2;
        mCenterY = (float) measureHeight(heightMeasureSpec) / 2;
    }

    /**
     * 如果是AT_MOST，specSize 代表的是最大可获得的空间；
     * 如果是EXACTLY，specSize 代表的是精确的尺寸；
     * 如果是UNSPECIFIED，对于控件尺寸来说，没有任何参考意义。
     *
     * @param widthMeasure
     * @return
     */
    private int measureWidth(int widthMeasure) {
        int specMode = MeasureSpec.getMode(widthMeasure);
        int specSize = MeasureSpec.getSize(widthMeasure);
        int result = mContext.getResources().getDisplayMetrics().widthPixels;
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    private int measureHeight(int heightMeasure) {
        int specMode = MeasureSpec.getMode(heightMeasure);
        int specSize = MeasureSpec.getSize(heightMeasure);
        int result = mContext.getResources().getDisplayMetrics().heightPixels;
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

}
