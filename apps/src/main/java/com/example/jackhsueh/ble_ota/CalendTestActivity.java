package com.example.jackhsueh.ble_ota;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.DecimalFormat;

public class CalendTestActivity extends AppCompatActivity {
    private String TAG= CalendTestActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calend_test);
        DecimalFormat df = new DecimalFormat("0.00");
        String format = df.format(1000000 / 1000.0 / 60.0 / 60);
        Log.i(TAG,format);//答案是正确的  保留精度的 小时 值。
    }
}
