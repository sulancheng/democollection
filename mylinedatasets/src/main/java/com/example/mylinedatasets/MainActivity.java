package com.example.mylinedatasets;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnChartValueSelectedListener {

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setSelected(false);

        // mMonthChart.setVisibleXRangeMaximum();设置一页显示的x坐标的个数。
        //moveViewToX//移动到想要显示的地方
        // mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        //mChart.setPinchZoom(false);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);
        mChart.zoom(1.4f, 1f, mChart.getWidth(), 0);//可以拖动

        // add data
        setData(20, 30);

        mChart.animateXY(500,2000);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();//最底部线的名字。
        l.setEnabled(true);
        // modify the legend ...
//        l.setForm(Legend.LegendForm.LINE);
//        l.setTextSize(11f);
//        l.setTextColor(Color.WHITE);
        // l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        // l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //  l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaxValue(5f);
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaxValue(123);
        leftAxis.setLabelCount(6, true);
        leftAxis.setAxisMinValue(0);
        // leftAxis.setGranularityEnabled(false);
        leftAxis.setDrawAxisLine(false);//左边竖线
        leftAxis.setDrawLabels(true);//左边的值
        leftAxis.setDrawGridLines(true);//横线

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
//        rightAxis.setInverted(false);
//        rightAxis.setTextColor(Color.RED);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<>();
        yVals1.add(new Entry(1, 100));
        yVals1.add(new Entry(1, 120));


        ArrayList<Entry> yVals2 = new ArrayList<>();
        yVals2.add(new Entry(2, 50));
        yVals2.add(new Entry(2, 100));

        ArrayList<Entry> yVals3 = new ArrayList<>();
        yVals3.add(new Entry(3, 60));
        yVals3.add(new Entry(3, 120));

        ArrayList<Entry> yVals4 = new ArrayList<>();
        yVals4.add(new Entry(4, 55));
        yVals4.add(new Entry(4, 110));

        ArrayList<Entry> yVals5 = new ArrayList<>();
        yVals5.add(new Entry(4.5f, 60));
        yVals5.add(new Entry(4.5f, 100));

        LineDataSet set1, set2, set3, set4,set5;
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.clear();
        set1 = new LineDataSet(yVals1, "DataSet 1");

        // set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);//半径
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        dataSets.add(set1);
        //set1.setFillFormatter(new MyFillFormatter(0f));
        //set1.setDrawHorizontalHighlightIndicator(false);
        //set1.setVisible(false);
        //set1.setCircleHoleColor(Color.WHITE);

        // create a dataset and give it a type
        set2 = new LineDataSet(yVals2, "DataSet 2");
        // set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.WHITE);
        set2.setLineWidth(2f);
        set2.setCircleRadius(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setCircleColor(Color.RED);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        dataSets.add(set2);
        //set2.setFillFormatter(new MyFillFormatter(900f));

        set3 = new LineDataSet(yVals3, "DataSet 3");
        // set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set3.setColor(Color.RED);
        set3.setCircleColor(Color.RED);
        set3.setLineWidth(2f);
        set3.setCircleRadius(3f);
        set3.setFillAlpha(65);
        set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set3.setDrawCircleHole(false);
        set3.setHighLightColor(Color.rgb(244, 117, 117));
        dataSets.add(set3);

        set4 = new LineDataSet(yVals4, "DataSet 4");
        // set4.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set4.setColor(Color.RED);
        set4.setCircleColor(Color.RED);
        set4.setLineWidth(2f);
        set4.setCircleRadius(3f);
        set4.setFillAlpha(65);
        set4.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set4.setDrawCircleHole(false);
        set4.setHighlightEnabled(true);
        set4.setDrawHighlightIndicators(true);//不要十字架
        set4.setHighLightColor(Color.WHITE); // 高亮的线的颜色
        set4.setHighlightLineWidth(3f);
        dataSets.add(set4);

        set5 = new LineDataSet(yVals5, "DataSet 5");
        // set4.setAxisDependency(YAxis.AxisDependency.RIGHT);   //看右边的轴
        set5.setColor(Color.RED);
        //set5.setCircleColor(Color.TRANSPARENT);
        set5.setLineWidth(1f);
        set5.setCircleRadius(0f);
        set5.setFillAlpha(0);
        //set5.setFormSize(20f); //线名字左边的框框 大小。
        set5.setDrawValues(false);
        set5.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set5.setDrawCircleHole(false);
        //set5.setHighLightColor(Color.rgb(244, 117, 117));
        dataSets.add(set5);

//            setsetzhi(set4,yVals4);
//            setsetzhi(set5,yVals5);
        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);


//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
//            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
//            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
//            set4 = (LineDataSet) mChart.getData().getDataSetByIndex(3);
//            set1.setValues(yVals1);
//            set2.setValues(yVals2);
//            set3.setValues(yVals3);
//            set4.setValues(yVals4);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            // create a dataset and give it a type
//            set1 = new LineDataSet(yVals1, "DataSet 1");
//
//            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//            set1.setColor(Color.RED);
//            set1.setCircleColor(Color.RED);
//            set1.setLineWidth(2f);
//            set1.setCircleRadius(3f);
//            set1.setFillAlpha(65);
//            set1.setFillColor(ColorTemplate.getHoloBlue());
//            set1.setHighLightColor(Color.rgb(244, 117, 117));
//            set1.setDrawCircleHole(false);
//            dataSets.add(set1);
//            //set1.setFillFormatter(new MyFillFormatter(0f));
//            //set1.setDrawHorizontalHighlightIndicator(false);
//            //set1.setVisible(false);
//            //set1.setCircleHoleColor(Color.WHITE);
//
//            // create a dataset and give it a type
//            set2 = new LineDataSet(yVals2, "DataSet 2");
//            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
//            set2.setColor(Color.RED);
//            set2.setCircleColor(Color.WHITE);
//            set2.setLineWidth(2f);
//            set2.setCircleRadius(3f);
//            set2.setFillAlpha(65);
//            set2.setFillColor(Color.RED);
//            set2.setDrawCircleHole(false);
//            set2.setCircleColor(Color.RED);
//            set2.setHighLightColor(Color.rgb(244, 117, 117));
//            dataSets.add(set2);
//            //set2.setFillFormatter(new MyFillFormatter(900f));
//
//            set3 = new LineDataSet(yVals3, "DataSet 3");
//            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
//            set3.setColor(Color.RED);
//            set3.setCircleColor(Color.RED);
//            set3.setLineWidth(2f);
//            set3.setCircleRadius(3f);
//            set3.setFillAlpha(65);
//            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
//            set3.setDrawCircleHole(false);
//            set3.setHighLightColor(Color.rgb(244, 117, 117));
//            dataSets.add(set3);
//
//            set4 = new LineDataSet(yVals3, "DataSet 4");
//            set4.setAxisDependency(YAxis.AxisDependency.RIGHT);
//            set4.setColor(Color.RED);
//            set4.setCircleColor(Color.RED);
//            set4.setLineWidth(2f);
//            set4.setCircleRadius(3f);
//            set4.setFillAlpha(65);
//            set4.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
//            set4.setDrawCircleHole(false);
//            set4.setHighLightColor(Color.rgb(244, 117, 117));
//            dataSets.add(set4);
////            setsetzhi(set4,yVals4);
////            setsetzhi(set5,yVals5);
//            // create a data object with the datasets
//            LineData data = new LineData(dataSets);
//            data.setValueTextColor(Color.WHITE);
//            data.setValueTextSize(9f);
//
//            // set data
//            mChart.setData(data);

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("onValueSelected",h.getDataIndex()+"  ,,");
        h.getDataIndex();

    }

    @Override
    public void onNothingSelected() {

    }
}
