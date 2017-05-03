package com.example.su.mygzzx.rxjava;

import android.app.Activity;
import android.os.Bundle;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by su
 * on 2017/3/17.
 */
public class HelloRxjava extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hello();
    }

    private void hello() {
       Flowable.just("Hello world").subscribe(new Consumer<String>() {
           @Override
           public void accept(@NonNull String s) throws Exception {
               System.out.println(s);
           }
       });
    }
}
