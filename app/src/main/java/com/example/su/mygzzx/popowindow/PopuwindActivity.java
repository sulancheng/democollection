package com.example.su.mygzzx.popowindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.su.mygzzx.R;

public class PopuwindActivity extends Activity {

    private EditText ed_email;
    private EditText ed_email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popuwind);

        initedit();
        ImageButton ib_setting = (ImageButton) findViewById(R.id.ib_setting);
        Button more = (Button) findViewById(R.id.more);
        ed_email1 = (EditText) findViewById(R.id.ed_email);
        ib_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inflate = View.inflate(PopuwindActivity.this, R.layout.tips, null);
//                PopupWindow pw = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, true);
                PopupWindow pw = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                // 设置点击返回键使其消失，且不影响背景，此时setOutsideTouchable函数即使设置为false
                // 点击PopupWindow 外的屏幕，PopupWindow依然会消失；相反，如果不设置BackgroundDrawable
                // 则点击返回键PopupWindow不会消失，同时，即时setOutsideTouchable设置为true
                // 点击PopupWindow 外的屏幕，PopupWindow依然不会消失
                pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pw.setOutsideTouchable(false); // 设置是否允许在外点击使其消失，到底有用没？
                //pw.setAnimationStyle(R.style.PopupAnimation); // 设置动画

                // 计算x轴方向的偏移量，使得PopupWindow在Title的正下方显示，此处的单位是pixels
                //int xoffInPixels = ScreenTools.getInstance(PopDemoActivity.this).getWidth() / 2 - titleName.getWidth() / 2;
                // 将pixels转为dip
                //int xoffInDip = ScreenTools.getInstance(PopDemoActivity.this).px2dip(xoffInPixels);
                //pw.showAsDropDown(ib_setting, -4, 0);
                //pw.showAtLocation(getLayoutInflater().inflate(R.layout.activity_popuwind, null), Gravity.LEFT, 0, 0);
                pw.showAsDropDown(ib_setting);
                pw.update();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mydialog mydialog = new Mydialog(PopuwindActivity.this);
                mydialog.show();
            }
        });


        ed_email1.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ed_email1.getWindowToken(), 0);
                    Log.i("ed_email", ed_email1.getText().toString().trim());//写你要做的事情
                    return true;
                }
                return false;

            }

        });
    }
    /**
     * Created by su 
     * on 2017/4/19 10:13
     */
    private void initedit() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root);
        controlKeyboardLayout(linearLayout, ed_email1);
    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        // 注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                        int rootInvisibleHeight = root.getRootView()
                                .getHeight() - rect.bottom;
                        Log.i("tag", "最外层的高度" + root.getRootView().getHeight());
                        // 若rootInvisibleHeight高度大于100，则说明当前视图上移了，说明软键盘弹出了
                        if (rootInvisibleHeight > 100) {
                            //软键盘弹出来的时候
                            int[] location = new int[2];
                            // 获取scrollToView在窗体的坐标
                            scrollToView.getLocationInWindow(location);
                            // 计算root滚动高度，使scrollToView在可见区域的底部
                            int srollHeight = (location[1] + scrollToView
                                    .getHeight()) - rect.bottom;
                            root.scrollTo(0, srollHeight);
                        } else {
                            // 软键盘没有弹出来的时候
                            root.scrollTo(0, 0);
                        }
                    }
                });
    }

    public void morepoop(View view) {
        View inflate = View.inflate(PopuwindActivity.this, R.layout.tips, null);
        PopupWindow pw = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置点击返回键使其消失，且不影响背景，此时setOutsideTouchable函数即使设置为false
        // 点击PopupWindow 外的屏幕，PopupWindow依然会消失；相反，如果不设置BackgroundDrawable
        // 则点击返回键PopupWindow不会消失，同时，即时setOutsideTouchable设置为true
        // 点击PopupWindow 外的屏幕，PopupWindow依然不会消失
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setOutsideTouchable(false); // 设置是否允许在外点击使其消失，到底有用没？
        //pw.setAnimationStyle(R.style.PopupAnimation); // 设置动画

        // 计算x轴方向的偏移量，使得PopupWindow在Title的正下方显示，此处的单位是pixels
        //int xoffInPixels = ScreenTools.getInstance(PopDemoActivity.this).getWidth() / 2 - titleName.getWidth() / 2;
        // 将pixels转为dip
        //int xoffInDip = ScreenTools.getInstance(PopDemoActivity.this).px2dip(xoffInPixels);
        //pw.showAsDropDown(ib_setting, -4, 0);
        pw.showAtLocation(getLayoutInflater().inflate(R.layout.activity_popuwind, null), Gravity.LEFT, 0, 0);
        //pw.showAsDropDown(ib_setting);
        pw.update();
    }
}
