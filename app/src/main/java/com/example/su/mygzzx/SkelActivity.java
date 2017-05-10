package com.example.su.mygzzx;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.su.mygzzx.xuanfochung.FloatWindowManager;
import com.example.su.mygzzx.xuanfochung.FloatWindowService;
import com.example.su.mygzzx.xuanfochung.FloatWindowSmallView;

public class SkelActivity extends Activity {
    private FloatWindowManager floatWindowManager;
    private Context context;
    private Button rxxiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skel);
        rxxiao = (Button) findViewById(R.id.rxxiao);
        //失败
        context = this;
        requestMultiplePermissions();
        floatWindowManager = FloatWindowManager.getInstance(context);
        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return;
            } else {
                //绘ui代码, 这里说明6.0系统已经有权限了
                //show();
            }
        } else {
            //绘ui代码,这里android6.0以下的系统直接绘出即可
           //show();
        }
        //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)rxxiao.getLayoutParams();
        //layoutParams.leftMargin = 30;
        //rxxiao.setLayoutParams(layoutParams);
        rxxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zuanbiaozhi();
            }
        });

    }

    private void zuanbiaozhi() {
        Log.i("locatopn "," l= "+rxxiao.getLeft()+" r ="+rxxiao.getRight()+" t= "+rxxiao.getTop()+" b = "+rxxiao.getBottom());
        //l= 0 r =360 t= 1200 b = 1380
        int[] locate = new int[2];
        rxxiao.getLocationOnScreen(locate);
        int lx = locate[0];
        int tx = locate[1];
        if (lx == 0 || lx == 0) {
//            Rect rect = new Rect();
//            rxxiao.getGlobalVisibleRect(rect);
//            lx = rect.left;
//            lx = rect.top;
        }
        int rx = lx + rxxiao.getWidth();
        int bx = tx + rxxiao.getHeight();

        Log.i("locatopn "," l1= "+lx+" r1 ="+rx+" t1 = "+tx+" b1 = "+bx);
        //l1= 0 r1 =360 t1 = 1272 b1 = 1452
        Log.i("locatopn "," with = "+rxxiao.getWidth()+" heigth = "+rxxiao.getHeight());

        Rect rect = new Rect();
        // 获取root在窗体的可视区域
        rxxiao.getWindowVisibleDisplayFrame(rect);
        Log.i("locatopn "," l2= "+rect.left+" r2 ="+rect.right+" t2 = "+rect.top+" b2= "+rect.bottom);
        //l2= 0 r2 =1080 t2 = 72 b2= 1920
    }

    /**
     * 显示小窗口  *  * @param view
     */
    public void show() {  // 需要传递小悬浮窗布局，以及根布局的id，启动后台服务
        Log.i("onRequestPermission","显示flow");
        Intent intent = new Intent(context, FloatWindowService.class);
        intent.putExtra(FloatWindowService.LAYOUT_RES_ID, R.layout.float_window_small);
        intent.putExtra(FloatWindowService.ROOT_LAYOUT_ID, R.id.small_window_layout);
        startService(intent);
    }
    private void requestMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean b5 = this.checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW) == PackageManager.PERMISSION_GRANTED;

//            PermissionUtils.requestPermission(this,1000,Manifest.permission.WRITE_EXTERNAL_STORAGE,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.READ_PHONE_STATE,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.ACCESS_FINE_LOCATION,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.READ_CONTACTS,true);

            Log.i("onRequestPermission","b5 = "+b5);
            if (!b5){
                String[] permissions = new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW};
                requestPermissions(permissions, 2000);
            } else {
                show();
            }
        } else {
            show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("onRequestPermission","onRequestPermissionsResult");
        //show();
        boolean flag = false;
        if (requestCode == 2000) {
            for (int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("onRequestPermission","grantResults");
                    flag = true;
                    break;
                }
            }
            if(flag){
                Log.i("onRequestPermission","权限申请");
               // startAppSettings();
            }else {
                show();
            }
        }
    }
    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 显示二级悬浮窗  *  * @param view
     */
    public void showBig(View view) {  // 设置小悬浮窗的单击事件
        floatWindowManager.setOnClickListener(new FloatWindowSmallView.OnClickListener() {
            @Override
            public void click() {
                floatWindowManager.createBigWindow(context);
            }
        });
    }

    /**
     * 移除所有的悬浮窗  *  * @param view
     */
    public void remove(View view) {
        floatWindowManager.removeAll();
    }

    public void rxtiaozuan(View  view){
        //startActivity(new Intent(SkelActivity.this, RxActivity.class));
    }
}

