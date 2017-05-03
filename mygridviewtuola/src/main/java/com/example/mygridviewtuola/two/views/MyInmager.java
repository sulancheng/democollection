package com.example.mygridviewtuola.two.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by su
 * on 2017/4/1.
 */
public class MyInmager extends ImageView {
    public MyInmager(Context context) {
        this(context,null);
    }

    public MyInmager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyInmager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
