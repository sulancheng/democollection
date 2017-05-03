package com.example.su.mygzzx.popowindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.su.mygzzx.R;

/**
 * Created by su
 * on 2017/4/18.
 */
public class Mydialog extends Dialog implements AdapterView.OnItemClickListener {

    private ListView lvStyle;
    private Context mcontext;
    private String[] items = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};

    private int[] bgs = new int[]{R.drawable.address_normal, R.drawable.address_orangle, R.drawable.address_blue, R.drawable.address_gray, R.drawable.address_green};

    public Mydialog(Context context) {
        this(context, 0);
    }

    public Mydialog(Context context, int themeResId) {
        super(context, R.style.AddressDialog);
        mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address_style);
        RelativeLayout all = (RelativeLayout) findViewById(R.id.all);
//        all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        //params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL; // 向下对齐
        params.gravity = Gravity.FILL | Gravity.CENTER_VERTICAL; //
        //lvStyle = (ListView) findViewById(R.id.lvStyle);
        //lvStyle.setAdapter(new StyleAdapter());
        //lvStyle.setOnItemClickListener(this);
        /*
* 将对话框的大小按屏幕大小的百分比设置
*/
//        WindowManager m = getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
//        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.95
//        getWindow().setAttributes(p);
    }

    class StyleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {

            return items[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return convertView;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
    }
}
