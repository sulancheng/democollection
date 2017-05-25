package com.example.jackhsueh.ble_ota.calendaranddaojshi;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jackhsueh.ble_ota.R;

public class Daojs2Act extends AppCompatActivity {
    private int recLen = 0;
    private TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daojs2);
        txtView = (TextView) findViewById(R.id.tv_tex);
        startDjs(5);

    }

    private void startDjs(int time) {
        recLen = time;
        handler.postDelayed(runnable, 1000);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(recLen>0){
                recLen--;
                txtView.setText("" + recLen);
                handler.postDelayed(this, 1000);
            }else {

            }
        }
    };

}
