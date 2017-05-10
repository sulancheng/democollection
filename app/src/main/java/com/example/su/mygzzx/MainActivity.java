package com.example.su.mygzzx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.su.mygzzx.tablyout.TablyoutActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeZone timeZoneSH = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone timeZoneNY = TimeZone.getTimeZone("America/New_York");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        inputFormat.setTimeZone(timeZoneSH);
        Date date1 = null;
        try {
            date1 = inputFormat.parse("1993-04-25 06:06:06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //不加时区
        Log.i("MainActivitytime",date1+"===geshihua="+date1.getTime());//Sun Apr 25 06:06:06 GMT+08:00 1993===geshihua=735689166000
        Log.i("MainActivitytime",inputFormat.format(date1)+"===geshihua=");// 1993-04-25 06:06:06===geshihua=
        inputFormat.setTimeZone(timeZoneNY);

        Log.i("MainActivitytime2",inputFormat.format(date1)+"===geshihua2="+inputFormat.format(date1));//: 1993-04-24 18:06:06===geshihua2=1993-04-24 18:06:06
        //startActivity(new Intent(this,RecycleActivity.class));
        getmothtime();
    }
    public void getmothtime(){
        TimeZone timeZoneSH = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        instance.setTimeZone(timeZoneSH);
        instance2.setTimeZone(timeZoneSH);
        int i = instance.get(Calendar.DAY_OF_WEEK)-1;
        Log.i("getmothtime",i+"");
        int q = 0;
        int x = 0;
        if(i!=1){
            q = i-1;
        }
        if(i!=7){
            x = 7-i;
        }
        instance.add(Calendar.DAY_OF_WEEK,-q);
        instance2.add(Calendar.DAY_OF_WEEK,x);
        Log.i("getmothtime",instance.getTime()+"===two="+instance2.getTime());

        startActivity(new Intent(MainActivity.this,SkelActivity.class));
    }
    public void tablyout(View view){
        startActivity(new Intent(this, TablyoutActivity.class));
    }
}
