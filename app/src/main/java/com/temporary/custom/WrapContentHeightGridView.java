package com.temporary.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * theme:
 * author：wyy
 */
public class WrapContentHeightGridView extends GridView {
    public WrapContentHeightGridView(Context context) {
        super(context);
    }

    public WrapContentHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentHeightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WrapContentHeightGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
