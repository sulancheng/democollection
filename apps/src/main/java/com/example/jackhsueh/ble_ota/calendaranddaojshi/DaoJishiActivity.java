package com.example.jackhsueh.ble_ota.calendaranddaojshi;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jackhsueh.ble_ota.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sucheng
 * on 2017/5/16.
 */
public class DaoJishiActivity extends Activity {
    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daojishi);
        mTvShow = (TextView) findViewById(R.id.show);
        shijianle2i();
    }
    /**
     * 取消倒计时
     * @param v
     */
    public void oncancel(View v) {
        timer.cancel();
    }

    /**
     * 开始倒计时
     * @param v
     */
    public void restart(View v) {
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(10000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mTvShow.setText((millisUntilFinished / 1000) + "秒后可重发");
            Log.i("logi","时间到了 做该做的事情！");
        }

        @Override
        public void onFinish() {
            mTvShow.setEnabled(true);
            //倒计时完成时候调用。
            mTvShow.setText("获取验证码");
        }
    };
    public void shijianlei(){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); //获取当前年份
        int mMonth = c.get(Calendar.MONTH)+1;//获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
        int mHour = c.get(Calendar.HOUR_OF_DAY);//获取当前的小时数
        int mMinute = c.get(Calendar.MINUTE);//获取当前的分钟数
        Log.i("shijianlei",mYear+"-"+mMonth+"-"+mDay+"-"+mHour+"-"+mMinute);
    }
    public void shijianle2i(){
        Calendar c = Calendar.getInstance();
        Calendar c0 = c;
        Date date1 = c.getTime();
        int mYear = c.get(Calendar.YEAR); //获取当前年份
        //c.add(Calendar.MONTH,1);//也是一个月之后 跟下面一样。
        // subtract 2 months from the calendar
        // cal.add(Calendar.MONTH, -2);
        //System.out.println("2 months ago: " + cal.getTime());
        c.set(Calendar.MONTH,c.get(Calendar.MONTH)+1+1);//退后一个月
        int mMonth = c.get(Calendar.MONTH);//获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
        c.set(Calendar.HOUR_OF_DAY,c.get(Calendar.HOUR_OF_DAY)+1);//往后推一个小时
        int mHour = c.get(Calendar.HOUR_OF_DAY);//获取当前的小时数
        int mMinute = c.get(Calendar.MINUTE);//获取当前的分钟数
        Date date2 = c.getTime();
        Log.i("shijianlei",c0+"====="+mYear+"-"+mMonth+"-"+mDay+"-"+mHour+"-"+mMinute+"====="+date1+"--"+date1.getTime()
                +"===="+date2+"--"+date2.getTime());



        //更加丰富的操作
       /* Calendar中add()和roll()函数的用法
        一、取某个时间点后的整点时刻。例如1984年7月7日15:23:05后的整点时刻即为1984-07-07 16:00:00。
        实现如下：*/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);//小时上加1
        calendar.set(Calendar.MINUTE, 0);//分钟设为0
        calendar.set(Calendar.SECOND, 0);//秒钟设为0

        //二、取某个日历之前的某个月。例如要取2002年1月12号之前1个月的时间，应该是2001年12月12日。
        Calendar calendar3 = Calendar.getInstance();
        calendar.set(2002, 0, 12);//代表2002年1月12日
        //calendar.set(Calendar.HOUR_OF_DAY,0);设置为零
        //calendar.clear(Calendar.MONTH);//清零
        calendar.add(Calendar.MONTH, -1);//这样就将日期设置成了2001年12月12日。
        System.out.println(calendar.getTime().toString());

        //但是这种情况如果用roll()来实现。如以下代码：
        Calendar calendar4 = Calendar.getInstance();
        calendar.set(2002, 0, 12);//代表2002年1月12日
        calendar.roll(Calendar.MONTH, -1);//这样就将日期设置成了2002年12月12日。
        System.out.println(calendar.getTime().toString());
    }
    /**
     * 计算两个日期之间相差的天数
     */
    public  void main(String[] args) {
        //设置两个日期
        //日期：2009年3月11号
        Calendar c1 = Calendar.getInstance();
        c1.set(2009, 3 - 1, 11);
        //日期：2010年4月1号
        Calendar c2 = Calendar.getInstance();
        c2.set(2010, 4 - 1, 1);
        //转换为相对时间
        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();
        //只知道时间戳。
        //c1.setTimeInMillis();
        //计算天数
        long days = (t2 - t1)/(24 * 60 * 60 * 1000);
        System.out.println(days);
    }

    //当前凌晨。
    public Calendar getDateStart(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }
}
