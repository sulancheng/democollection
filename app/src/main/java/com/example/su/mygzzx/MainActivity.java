package com.example.su.mygzzx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.su.mygzzx.recycleview.RecycleActivity;

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
        init();
        TimeZone timeZoneSH = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone timeZoneNY = TimeZone.getTimeZone("America/New_York");
        TimeZone timeZoneNZ = TimeZone.getTimeZone("UTC");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //inputFormat.setTimeZone(timeZoneSH);
        Date date1 = null;
        try {
            date1 = inputFormat.parse("1993-04-25 06:06:06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //不加时区  重要date.getime是跟时区没有任何关系的。
        Log.i("MainActivitytime",date1+"===geshihua="+date1.getTime());//Sun Apr 25 06:06:06 GMT+08:00 1993===geshihua=735689166000
        Log.i("MainActivitytime",inputFormat.format(date1)+"===geshihua=");// 1993-04-25 06:06:06===geshihua=
        //inputFormat.setTimeZone(timeZoneNY);
        //Log.i("MainActivitytime2",inputFormat.format(date1)+"===geshihua2="+date1.getTime());//: 1993-04-24 18:06:06===geshihua2=1993-04-24 18:06:06

        Calendar instance = Calendar.getInstance();
        instance.setTime(date1);//735689166000
        //one:这里是同一个时间戳，设置成UTC的时区，打印的时间字符串Sat Apr 24 22:06:06 UTC 1993============735689166000
        //instance.setTimeZone(timeZoneNZ);
        TimeZone atDefault = TimeZone.getDefault();
        TimeZone.setDefault(timeZoneNZ);
        Log.i("MainActivitytime3",instance.getTime()+"============"+instance.getTime().getTime());
        TimeZone.setDefault(atDefault);
        //two：这里是做了偏移的时间戳，设置成默认时区 time = 735689166000 rawoffset=0===Sat Apr 24 22:06:06 UTC 1993
        int rawOffsetfset = TimeZone.getDefault().getRawOffset();//获取当前时区到UTC的时间差。
        long time = date1.getTime();
        Date date = new Date(time - rawOffsetfset);
//        //TimeZone.setDefault(timeZoneSH);
        Log.i("MainActivitytime4","time = "+time +" rawoffset="+ rawOffsetfset +"==="+date);

        //startActivity(new Intent(this,RecycleActivity.class));
        getmothtime();
    }

    private void init() {
        GridView jggridview = (GridView) findViewById(R.id.jggridview);
        int [] aa ={1,2,3,4,5,6,7,8,9,0};
        jggridview.setAdapter(new Gradadapter(aa));

//        jggridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i("onItemClick"," i ="+i +" l ="+l);
//            }
//        });
        jggridview.setOnItemClickListener((a,v,i,l)->Log.i("onItemClick"," i ="+i +" l ="+l)
        );

    }
class Gradadapter extends BaseAdapter{
    int[] bb ;
    public Gradadapter(int [] aa){
        this.bb = aa;
    }
    @Override
    public int getCount() {
        return bb.length;
    }

    @Override
    public Object getItem(int i) {
        return bb[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = View.inflate(MainActivity.this, R.layout.graditem,null);
        TextView text = (TextView) convertView.findViewById(R.id.text);
        text.setText(bb[i]+"");
        return convertView;
    }
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

        //startActivity(new Intent(MainActivity.this,SkelActivity.class));
    }
    public void tablyout(View view){
        startActivity(new Intent(this, RecycleActivity.class));
    }
}
