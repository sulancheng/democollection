package com.example.su.mygzzx.piacasso;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.su.mygzzx.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {

    private ImageView icpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        icpic = (ImageView) findViewById(R.id.iv_pic);
        byte[] photo = {1,2,3};
        Picasso.with(this)
                .load("haha")
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(icpic, new Callback() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError() {
                        Picasso.with(Main2Activity.this).invalidate("two");
                    }
                });

        //        Picasso.with(context).load(url)
//                .skipMemoryCache()
//                .into(imageView);
//        提示:skipMemoryCache()方法已被废弃,若无法达成效果可采用下面这种
//
//        Picasso.with(context).load(url)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .error(R.drawable.default_icon)
//                .into(imageView);
    }
    private void loadImageCache(String url) {
//        String sdcardPath = SdCardUtil.getSdCardPath("lenovo/cache/");
//        picasso = new Picasso.Builder(this).downloader(
//                new OkHttpDownloader(new File(sdcardPath))).build();
//        Picasso.setSingletonInstance(picasso);
        //Picasso.with(this).invalidate();//没有缓存 参数是清楚缓存的文件位置
        //picasso.with(this).load("http://www.hchlnet.com/portrait/lenovo.png").into(iv_splash);

//        Picasso.with(this).load("http://www.hchlnet.com/portrait/lenovo.png")
//                .memoryPolicy(MemoryPolicy.NO_CACHE，MemoryPolicy.NO_STORE)
//                .networkPolicy(NetworkPolicy.NO_CACHE，NetworkPolicy.NO_STORE)
//                .error(R.drawable.lx_guide)
//                .into(iv_splash);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(5, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(3, TimeUnit.SECONDS);
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttpDownloader(okHttpClient))
                .build();
        picasso.with(this).load(url).memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .config(Bitmap.Config.ARGB_8888).into(icpic,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });
    }
}
