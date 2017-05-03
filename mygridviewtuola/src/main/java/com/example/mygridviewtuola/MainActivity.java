package com.example.mygridviewtuola;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl_strues;
    /**
     * item删除的时候的动画层
     */
    RelativeLayout ll;

    private DragAdapter adapter;
    private DragGrid gridview;
    private Context mContext;
    //顶部导航的高度
    public static int StruesHeight;
    /**
     * 获取gridview测量后的高度
     */
    int height;
    /**
     * 判断是否正在移动
     */
    private boolean isMove = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        this.mContext = this;
        gridview = (DragGrid)findViewById(R.id.gradview);
        rl_strues = (RelativeLayout)findViewById(R.id.rl_strues);
        ll = (RelativeLayout)findViewById(R.id.ll);

        initDensityDpi();

        ViewTreeObserver vto = rl_strues.getViewTreeObserver();
        vto.addOnPreDrawListener(new OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                // TODO Auto-generated method stub
                StruesHeight = rl_strues.getMeasuredHeight();
                return true;
            }
        });
    }


    /**
     * 获取手机的屏幕密度DPI
     */
    private void initDensityDpi() {
        // TODO Auto-generated method stub
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Common.Width = metrics.widthPixels;
        Common.Height = metrics.heightPixels;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initDate();

        ViewTreeObserver vto = gridview.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                gridview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = gridview.getHeight();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initDate() {
        // TODO Auto-generated method stub
        adapter = new DragAdapter(mContext, getList(), gridview);
        gridview.setRelativeLayout(ll);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (isMove) {
                    return;
                }
                DragView dragView = (DragView)parent.getItemAtPosition(position);
                if (!dragView.getName().equals("更多")) {
                   // gridview.deleteInfo(position);
                    Toast.makeText(mContext, dragView.getName(), Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(MainActivity.this, DraggableGridViewSampleActivity.class));
                }
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int DownY = (int)event.getY();
                if (DownY - height - MainActivity.StruesHeight> 0) {
                    Common.isDrag = false;
                    gridview.refresh();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取初始化数据
     * @param
     * @return
     */
    private List<DragView> getList() {
        // TODO Auto-generated method stub
        List<DragView> list = new ArrayList<DragView>();
        DragView jcxx = null;
        jcxx = new DragView();
        jcxx.setId(1);
        jcxx.setName("监测总量");
        jcxx.setResid(R.drawable.tubiao_10);
        list.add(jcxx);
        jcxx = new DragView();
        jcxx.setId(2);
        jcxx.setName("建设情况");
        jcxx.setResid(R.drawable.tubiao_12);
        list.add(jcxx);
        jcxx = new DragView();
        jcxx.setId(3);
        jcxx.setName("上线情况");
        jcxx.setResid(R.drawable.tubiao_14);
        list.add(jcxx);
        jcxx = new DragView();
        jcxx.setId(4);
        jcxx.setName("报警情况");
        jcxx.setResid(R.drawable.tubiao_19);
        list.add(jcxx);
        jcxx = new DragView();
        jcxx.setId(5);
        jcxx.setName("流量情况");
        jcxx.setResid(R.drawable.tubiao_20);
        list.add(jcxx);
        jcxx = new DragView();
        jcxx.setId(6);
        jcxx.setName("水质数据");
        jcxx.setResid(R.drawable.tubiao_21);
        list.add(jcxx);
        jcxx = new DragView();
        jcxx.setName("更多");
        jcxx.setResid(R.drawable.tubiao_25);
        list.add(jcxx);
        return list;
    }
}
