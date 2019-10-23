package com.temporary.util;

import android.content.Context;
import android.graphics.Color;

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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.temporary.demoproject.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dee on 2018/7/17.
 */

public class MPandroidChartUtil {

    /**
     * 曲线图
     *
     * @param context
     * @param title     曲线图标题
     * @param lineChart 曲线图组件
     * @param values    曲线图值
     * @param labels    曲线图X轴标识
     */
    public static void lineChartInitView(Context context, LineChart lineChart, String title,
                                         List<Float> values, final List<String> labels) {
        //设置边界
        lineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entityList = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            entityList.add(new Entry(i, values.get(i)));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entityList, title);
        //lineDataSet.setDrawCubic(true);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setHighlightEnabled(false);//取消定位线
        lineDataSet.setColor(context.getResources().getColor(R.color.colorPrimary));
        lineDataSet.setCircleColor(context.getResources().getColor(R.color.colorPrimary));
        lineDataSet.setDrawFilled(true);// 是否填充颜色
        lineDataSet.setDrawCircles(false);// 是否显示圆圈
        lineDataSet.setDrawValues(false);// 是否显示数值

        //设置X轴坐标在底部
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setEnabled(false);//隐藏X轴坐标和线
        xAxis.setDrawGridLines(false);//隐藏竖线

        //如果不添加此设置，x轴会显示不全
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size(), false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int) value % labels.size());
            }
        });

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setStartAtZero(true);
        yAxisLeft.setXOffset(10f);
        yAxisLeft.setDrawGridLines(false);//隐藏横线
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setStartAtZero(true);
        yAxisRight.setEnabled(true);//去掉右边Y轴显示
        yAxisRight.setDrawGridLines(false);//隐藏横线

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(10f);
        lineChart.setData(lineData);
        lineChart.setTouchEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
    }

    /**
     * 饼状图
     *
     * @param context
     * @param pieChart    饼状图组件
     * @param title       饼状图标题
     * @param centerTitle 饼状图中心文字
     * @param values      饼状图值
     * @param labels      饼状图分区标题
     */
    public static void pieChartInitView(Context context, PieChart pieChart, String title,
                                        String centerTitle, List<Float> values,
                                        List<String> labels) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);//去掉右下角的“Description Label”
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setTouchEnabled(false);
        //减速的摩擦系数在[0; 1]区间，数值越高表示速度会缓慢下降
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文字
        pieChart.setCenterText(centerTitle);
        pieChart.setDrawHoleEnabled(true);//是否为实心
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);//中心圈的颜色
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // 触摸旋转
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        //变化监听
        //mPieChart.setOnChartValueSelectedListener(this);

        //模拟数据
        ArrayList<PieEntry> entriesPie = new ArrayList<PieEntry>();
        for (int i = 0; i < labels.size(); i++) {
            entriesPie.add(new PieEntry(values.get(i), labels.get(i)));
        }

        //设置数据
        PieDataSet dataSet = new PieDataSet(entriesPie, title);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        //刷新
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//元素说明放在上边
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//元素说明放在右边
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);//元素说明竖排列
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        // 输入标签样式
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);//文字大小
    }

    /**
     * 堆叠柱形图
     *
     * @param context
     * @param barChart       柱形图组件
     * @param title          柱形图标题
     * @param values         柱形图数值
     * @param labels         柱形图数值标题
     * @param stackingTitles 堆叠柱形标题
     */
    public static void barChartStackingInitView(Context context, BarChart barChart, String title,
                                                List<Float[]> values, final List<String> labels,
                                                List<String> stackingTitles) {
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(40);
        barChart.setDrawBorders(true);//图标黑框
        barChart.setTouchEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(8f);
        legend.setFormToTextSpace(4f);
        legend.setXEntrySpace(6f);

        //解决X轴显示不全
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(labels.size(), false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int) value % labels.size());
            }
        });

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setStartAtZero(true);
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < labels.size(); i++) {
            float[] floats = new float[values.get(0).length];
            for (int j = 0; j < floats.length; j++) {
                floats[j] = (values.get(i))[j];
            }
            /*此处为堆叠柱形，堆叠数目根据数组长度而定*/
            yVals1.add(new BarEntry(i, floats));
        }

        //有尽可能多的颜色每项堆栈值
        int[] colors = new int[values.get(0).length];
        for (int i = 0; i < values.get(0).length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        BarDataSet set1;
        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, title);
            set1.setColors(colors);
            /*此处为堆叠柱形，堆叠数目根据数组长度而定*/
            String[] valueLabels = new String[stackingTitles.size()];
            for (int i = 0; i < valueLabels.length; i++) {
                valueLabels[i] = stackingTitles.get(i);
            }
            set1.setStackLabels(valueLabels);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.BLACK);

            barChart.setData(data);
        }
        barChart.setFitBars(false);
        barChart.invalidate();
    }
}
