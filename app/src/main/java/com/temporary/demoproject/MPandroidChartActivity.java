package com.temporary.demoproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.util.MPandroidChartUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图表demo
 */
public class MPandroidChartActivity extends AppCompatActivity {
    @BindView(R.id.LineChart)
    protected LineChart mLineChart;
    @BindView(R.id.pieChart)
    protected PieChart mPieChart;
    @BindView(R.id.barChart)
    protected BarChart mBarChart;
    @BindView(R.id.line_anim_x_button)
    protected Button mLineAnimXBtn;
    @BindView(R.id.line_anim_y_button)
    protected Button mLineAnimYBtn;
    @BindView(R.id.line_anim_xy_button)
    protected Button mLineAnimXYBtn;
    @BindView(R.id.pie_anim_x_button)
    protected Button mPieAnimXBtn;
    @BindView(R.id.pie_anim_y_button)
    protected Button mPieAnimYBtn;
    @BindView(R.id.pie_anim_xy_button)
    protected Button mPieAnimXYBtn;
    @BindView(R.id.bar_anim_x_button)
    protected Button mBarAnimXBtn;
    @BindView(R.id.bar_anim_y_button)
    protected Button mBarAnimYBtn;
    @BindView(R.id.bar_anim_xy_button)
    protected Button mBarAnimXYBtn;
    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.barChart_v2)
    BarChart mBarChartV2;
    private List<Integer> data = new ArrayList<>();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);
        ButterKnife.bind(this);

        initTop();

        List<Float> valuesLine = new ArrayList<>();
        List<String> labelsLine = new ArrayList<>();
        labelsLine.add("周一");
        labelsLine.add("周二");
        labelsLine.add("周三");
        labelsLine.add("周四");
        labelsLine.add("周五");
        labelsLine.add("周六");
        labelsLine.add("周日");
        for (int i = 0; i < labelsLine.size(); i++) {
            valuesLine.add((float) (Math.random()) * 80);
        }
        MPandroidChartUtil.lineChartInitView(this, mLineChart, "温度",
                valuesLine, labelsLine);

        String pieTitle = "三年级一班";
        String pieCenterTitle = "test";
        List<Float> valuesPie = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            valuesPie.add((float) (Math.random() * 80 + 40));
        }
        List<String> labelsPie = new ArrayList<>();
        labelsPie.add("优秀");
        labelsPie.add("满分");
        labelsPie.add("及格");
        labelsPie.add("不及格");
        MPandroidChartUtil.pieChartInitView(this, mPieChart, pieTitle, pieCenterTitle,
                valuesPie, labelsPie);

        List<String> stackingTitle = new ArrayList<>();
        List<Float[]> valuesBar = new ArrayList<>();
        List<String> labelsBar = new ArrayList<>();
        labelsBar.add("周一");
        labelsBar.add("周二");
        labelsBar.add("周三");
        labelsBar.add("周四");
        labelsBar.add("周五");
        labelsBar.add("周六");
        labelsBar.add("周日");
        for (int i = 0; i < labelsBar.size(); i++) {
            Float[] floats = new Float[3];
            for (int j = 0; j < floats.length; j++) {
                floats[j] = (float) (Math.random() * 80 + 40);
            }
            valuesBar.add(floats);
        }
        stackingTitle.add("未完成");
        stackingTitle.add("已完成");
        stackingTitle.add("待修");
        MPandroidChartUtil.barChartStackingInitView(this, mBarChart, "测试",
                valuesBar, labelsBar, stackingTitle);

        initBarchartV2();

        //one.start();

        new Thread(runnableWait).start();
    }

    private void initTop() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.mp_chart_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initBarchartV2() {
        //背景颜色
        mBarChartV2.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        mBarChartV2.setDrawGridBackground(false);
        //背景阴影
        mBarChartV2.setDrawBarShadow(false);
        mBarChartV2.setHighlightFullBarEnabled(false);
        //显示边框
        mBarChartV2.setDrawBorders(true);
        //设置动画效果
        mBarChartV2.animateY(1000);
        mBarChartV2.animateX(1000);

        mBarChartV2.getDescription().setEnabled(false);
        mBarChartV2.setTouchEnabled(false);

        /***XY轴的设置***/
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BarEntry barEntry = new BarEntry(i, (float) (Math.random() * 80 + 40));
            barEntries.add(barEntry);
        }

        //X轴设置显示位置在底部
        XAxis xAxis = mBarChartV2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(barEntries.size(), false);
        /*xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "";
            }
        });*/

        YAxis leftAxis = mBarChartV2.getAxisLeft();
        YAxis rightAxis = mBarChartV2.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setEnabled(false);

        /***折线图例 标签 设置***/
        Legend legend = mBarChartV2.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        legend.setEnabled(false);

        BarDataSet barDataSet = new BarDataSet(barEntries, "测试");
        barDataSet.setColor(getResources().getColor(R.color.theme_color_light));
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(true);

        BarData barData = new BarData(barDataSet);
        barData.setValueTextSize(11f);
        mBarChartV2.setData(barData);
    }

    @OnClick({R.id.line_anim_x_button, R.id.line_anim_y_button, R.id.line_anim_xy_button,
            R.id.pie_anim_x_button, R.id.pie_anim_y_button, R.id.pie_anim_xy_button,
            R.id.bar_anim_x_button, R.id.bar_anim_y_button, R.id.bar_anim_xy_button})
    protected void buttonOnclick(View view) {
        switch (view.getId()) {
            case R.id.line_anim_x_button: {//线性图x轴动画
                mLineChart.animateX(1000);
                //two.start();

                new Thread(runnableNotify).start();
                break;
            }
            case R.id.line_anim_y_button: {//线性图y轴动画
                mLineChart.animateY(1000);
                break;
            }
            case R.id.line_anim_xy_button: {//线性图xy轴动画
                mLineChart.animateXY(1000, 1000);
                break;
            }
            case R.id.pie_anim_x_button: {//饼状图x轴动画
                mPieChart.animateX(1000);
                break;
            }
            case R.id.pie_anim_y_button: {//饼状图y轴动画
                mPieChart.animateY(1000);
                break;
            }
            case R.id.pie_anim_xy_button: {//饼状图xy轴动画
                mPieChart.animateXY(1000, 1000);
                break;
            }
            case R.id.bar_anim_x_button: {//柱形图x轴动画
                mBarChart.animateX(1000);
                break;
            }
            case R.id.bar_anim_y_button: {//柱形图y轴动画
                mBarChart.animateY(1000);
                break;
            }
            case R.id.bar_anim_xy_button: {//柱形图xy轴动画
                mBarChart.animateXY(1000, 1000);
                break;
            }
        }
    }

    Thread one = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                synchronized (one) {
                    while (true) {
                        Log.e("wyy", "MPandroidChartActivity run 111");
                        one.wait();
                        Log.e("wyy", "MPandroidChartActivity run 222");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    Thread two = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (one) {
                //one.notify();
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }.notify();
            }
        }
    });

    Runnable runnableWait = new Runnable() {
        @Override
        public void run() {
            try {
                synchronized (runnableWait) {
                    while (true) {
                        Log.e("wyy", "MPandroidChartActivity run 111");
                        runnableWait.wait();
                        Log.e("wyy", "MPandroidChartActivity run 222");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable runnableNotify = new Runnable() {
        @Override
        public void run() {
            synchronized (runnableWait) {
                runnableWait.notify();
            }
        }
    };

}
