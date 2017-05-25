package com.example.jackhsueh.ble_ota.vitamiohs;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.jackhsueh.ble_ota.R;

/**
 * Created by sucheng
 * on 2017/5/16.
 */
public class VitamiohsActivity extends Activity{
    private ImageView fullornormalscreen;
    private boolean fullscreen = false;
    private VideoView mVideoView;
    private static int vWidth;
    private static int vHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vitamiohs);

        mVideoView = (VideoView) findViewById(R.id.videoView);
        fullornormalscreen = (ImageView) findViewById(R.id.fullornormalscreen);
        fullornormalscreen.setImageDrawable(getResources().getDrawable(R.drawable.icon_full));
        fullornormalscreen.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                String path = "";
                new MyClickTask().execute(path);
            }
        });
    }
    public class MyClickTask extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            String title;
            if (!fullscreen) {//设置RelativeLayout的全屏模式

                //   System.out.println("-----linearLayout_player_nba----false------->>：1" );
                VitamiohsActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                title = "窗口";
                //   LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)linearLayout_player_nba.getLayoutParams();
                fullscreen = true;//改变全屏/窗口的标记
            } else {//设置RelativeLayout的窗口模式

                VitamiohsActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                title = "全屏";
                fullscreen = false;//改变全屏/窗口的标记
            }

            return title;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == "窗口")
                fullornormalscreen.setImageDrawable(getResources().getDrawable(R.drawable.icon_normal));
            else
                fullornormalscreen.setImageDrawable(getResources().getDrawable(R.drawable.icon_full));

        }
    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // 检测屏幕的方向：纵向或横向
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //当前为横屏， 在此处添加额外的处理代码
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);//消除状态栏



            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;
            vWidth = width;
            ViewGroup.LayoutParams lp = mVideoView.getLayoutParams();
            lp.width = width;
            lp.height = height;

            mVideoView.setLayoutParams(lp);
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);//显示状态栏

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //当前为竖屏， 在此处添加额外的处理代码
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;
            vWidth = width;
            ViewGroup.LayoutParams lp = mVideoView.getLayoutParams();
            lp.width = width;
            lp.height = (int) (height * (1 - 0.618));

            mVideoView.setLayoutParams(lp);
            getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);//显示状态栏

        }


        //检测实体键盘的状态：推出或者合上
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            //实体键盘处于推出状态，在此处添加额外的处理代码
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            //实体键盘处于合上状态，在此处添加额外的处理代码
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
