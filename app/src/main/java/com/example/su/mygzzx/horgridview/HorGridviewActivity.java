package com.example.su.mygzzx.horgridview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.su.mygzzx.R;

import java.util.ArrayList;

public class HorGridviewActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hor_gridview);
        gridView = (GridView) findViewById(R.id.listView);
        for (int x = 0; x<10;x++){
            list.add("你好"+x);
        }
        horizontal_layout();//设置gridview为横向布局
        gridView.setAdapter(new Myadapter());
    }
    //gridview横向布局方法
    public void horizontal_layout(){
        int size = list.size();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (110 * size * density);
        int itemWidth = (int) (60 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params);// 设置GirdView布局参数
        gridView.setColumnWidth(itemWidth);// 列表项宽
        gridView.setHorizontalSpacing(0);// 列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);//总长度
    }
    class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = View.inflate(HorGridviewActivity.this,R.layout.activity_hor_gridview_item,null);
            }
           TextView tvname = (TextView) view.findViewById(R.id.coursename);
            tvname.setText(getItem(i).toString());
            return view;
        }
    }
}
