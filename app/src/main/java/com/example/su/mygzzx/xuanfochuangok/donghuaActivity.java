package com.example.su.mygzzx.xuanfochuangok;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.su.mygzzx.R;

/**
 * Created by sucheng
 * on 2017/5/4.
 */
public class donghuaActivity extends Activity {
    private boolean isAnimal = false;
    int zy = 0;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donghua);
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scrollview);
        ImageView xuanfu = (ImageView) findViewById(R.id.xuanfu);

        TranslateAnimation taright = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0.8f);
        TranslateAnimation taright2 = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.8f, TranslateAnimation.RELATIVE_TO_SELF, 0f);










        taright.setFillAfter(true);
        taright2.setFillAfter(true);
        taright.setDuration(500);
        taright2.setDuration(500);
        taright.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        scrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
//            @Override
//            public void onScroll(int dy) {
//                Log.i("dy","dy = "+dy);
//            }
//        });
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int oldx, int oldy) {
                Log.i("onScrollChange"," x = "+x +" y = "+y +" oldx = "+oldx+" oldy ="+oldy );
            }
        });
    }
}
