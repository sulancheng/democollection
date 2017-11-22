package com.example.mylinedatasets;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ActivityL extends AppCompatActivity {

    private LineChart chartline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_l);
        chartline = (LineChart) findViewById(R.id.chartline);
        chartline.setDragEnabled(true);
        chartline.setScaleEnabled(false);
        // 设置能否触摸 setBarSpacePercent
        chartline.setTouchEnabled(true);

        chartline.getLegend().setEnabled(false);
        //set1.setBarSpacePercent(10f); 柱形图设置间隔
        //mWidth = mDayChart.getWidth();
        //chartline.zoom(4, 1f, mWidth, 0);
        //chartline.setVisibleXRangeMaximum();//设置最大的课件数据，也可以使得统计图滑动
        chartline.zoom(4, 1f, 2, 0);//第一参数一显示一个值  柱形图的粗细， 第三参数显示的是柱形图宽度。


        //设置描述文字
        Description desc = new Description();
        desc.setText("7天走势图");
        chartline.setDescription(desc);

        XAxis xAxis = chartline.getXAxis();
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setSpaceMin(3); 将横坐标原点向右移动。
       // xAxis.setLabelCount(3, true);
        //xAxis.setDrawLabels(false);//底部坐标值
        //xAxis.setDrawAxisLine(false);
        //xAxis.setAvoidFirstLastClipping(false);
        xAxis.setDrawGridLines(false);//网格

        ArrayList<Entry> yValues = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            yValues.add(new Entry(i, i));
        }
        LineDataSet dataSet = new LineDataSet(yValues, "双色球");
        dataSet.setColor(Color.RED);

        //关闭右边的数据
        YAxis rightAxis = chartline.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis leftAxis = chartline.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaxValue(18);
        leftAxis.setLabelCount(6, true);
        leftAxis.setAxisMinValue(0);


        ArrayList<Entry> yValue1 = new ArrayList<>();

        yValue1.add(new Entry(0, 7));
        yValue1.add(new Entry(1, 17));
        yValue1.add(new Entry(2, 3));
        yValue1.add(new Entry(3, 5));
        yValue1.add(new Entry(4, 4));
        yValue1.add(new Entry(5, 3));
        yValue1.add(new Entry(6, 7));
        yValue1.add(new Entry(7, 7));
        yValue1.add(new Entry(8, 7));
        yValue1.add(new Entry(9, 7));
        yValue1.add(new Entry(10, 7));
        yValue1.add(new Entry(11, 7));
        yValue1.add(new Entry(12, 7));
        yValue1.add(new Entry(13, 7));
        yValue1.add(new Entry(14, 17));
        yValue1.add(new Entry(15, 3));
        yValue1.add(new Entry(16, 5));
        yValue1.add(new Entry(17, 4));
        yValue1.add(new Entry(18, 3));
        yValue1.add(new Entry(19, 7));
        yValue1.add(new Entry(20, 7));
        yValue1.add(new Entry(21, 8));
        yValue1.add(new Entry(22, 7));
        yValue1.add(new Entry(23,16));
        yValue1.add(new Entry(24, 7));
        yValue1.add(new Entry(25, 8));
        LineDataSet dataSet1 = new LineDataSet(yValue1, "七星彩");
        dataSet1.setColor(Color.BLACK);
        dataSet1.disableDashedLine();
        //dataSet1.setDrawCircles(false);//是否画圆
        //dataSet1.setDrawCubic(true);//圆滑


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        //将数据加入dataSets
        dataSets.add(dataSet);
        dataSets.add(dataSet1);

        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(dataSets);
        //将数据插入
        chartline.setData(lineData);
    }
}
