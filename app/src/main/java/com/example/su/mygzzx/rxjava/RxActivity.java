package com.example.su.mygzzx.rxjava;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.su.mygzzx.R;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends Activity {
    Button lamrd;
    ListView lv_lamrd;
    private String TAG= "RxActivity";
//设置观察者和发布者代码所要运行的线程后注册观察者
  /*  observable.subscribeOn(Schedulers.immediate())//在当前线程执行subscribe()方法
            .observeOn(AndroidSchedulers.mainThread())//在UI线程执行观察者的方法
            .subscribe(subscriber);*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        init();
        //关于lamda的简单应用
        lamrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lamrd.setOnClickListener(a->
             a.setVisibility(View.GONE)
        );
        lv_lamrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        lv_lamrd.setOnItemClickListener((p,v,po,i)->{

        });

        //创建一个被观察者(发布者)
//        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                subscriber.onNext(1001);
//                subscriber.onNext(1002);
//                subscriber.onNext(1003);
//                subscriber.onCompleted();
//            }
//        });
        //生命周期管理  and  内存泄漏。
//        compile 'com.trello:rxlifecycle:0.4.0'
//        compile 'com.trello:rxlifecycle-components:0.3.0'

        //创建一个观察者
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                //可用于取消订阅
               // d.dispose();
                //还可以判断是否处于取消状态
                //boolean b=d.isDisposed();
            }

            @Override
            public void onNext(Integer o) {
                Log.e(TAG, "timer(...)  onNext:" + o.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "timer(...)  onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "timer(...)  onCompleted555");
            }
        };

        //Just类似于From，但是From会将数组或Iterable的元素具取出然后逐个发射，而Just只是简单的原样发射，将数组或Iterable当做单个数据。
        //Just接受一至九个参数，返回一个按参数列表顺序发射这些数据的Observable


        //创建被观察者     同时是调用者。
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);//调用的是观察者的方法
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).timeout(1000, TimeUnit.MILLISECONDS, new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                observer.onError(new Error("错误的"));
            }
        });
        //注册后就会开始调用call()中的观察者执行的方法 onNext() onCompleted()等
       // observable.subscribe(observer);

    }

    private void init() {
        //登陆
        RxView.clicks(findViewById(R.id.bt_rx_bt))
                .throttleFirst(500,TimeUnit.MILLISECONDS)//过滤点击事件，500ms只接受一次
                .doOnNext(a->{
                    Log.i("rxandroidmy","点击事件");
                })
                .concatMap(a ->text1(2,3,""))
                .filter((resulet) -> {//过滤事件。
                    return resulet>4;
                })
                .doOnNext(param -> {
                    logtest("结果大于5");
                    timer();
                })
                .subscribe();


    }

    private void mapDemo() {

        //在2.0后命名规则有了改变

        //Action1--------Consumer一个参数的

        //Action2--------BiConsumer两个参数的
        //而Function的也一样
        Observable.just("图片存放路径").map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(String s) throws Exception {
               // return getBitmapFromFile(s);
                return null;
            }
        }).observeOn(Schedulers.io())//指定 subscribe() 发生在 新的 线程
                .subscribeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程(android特有)
                //这里的Action一个参数的改为COnsumer
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        //iv.setImageBitmap(bitmap);
                    }
                });
    }

    public interface test{
         void meth1();
         void meth2();
    }


    public void empty(){
    }
    public void from(){
        Integer[] items = { 0, 1, 2, 3, 4, 5 };
        Observable.fromArray(items)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG+"run","onNext   "+"我跑完了2");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG+"from","onNext   "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG+"run","onNext   "+"我跑完了1");
                    }
                });


    }
    public void rang(){
        Observable.range(5,3)
                .repeat(3)  //设置次数
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG+"rang","onNext   "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //成功
    public void timer(){
        Observable.just(true)
                //.timer(2000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
               // .doOnNext(a ->logtest("nihao"))
                .flatMap(a ->speak("今天RX入门！！！！！"))
                .doOnComplete(() ->logtest("我完成了speak"))//在其之上的flatMap 都完成了  就会调用。
                //.doOnNext(a ->logtest(a))
                .flatMap(a ->text1(3,7,"我成功了"))
                .filter((resulet) -> {//过滤事件。
                    return resulet>5;
                })
                .doOnNext(a ->logtest(a+""))
               // .flatMap(a ->onException())
                .doOnError(err -> logtest(err.getMessage()+"haha"))//没有报异常会崩溃
                .doOnComplete(() ->logtest("我完成了1"))
//                .doOnError(new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.e(TAG,"Send error : " + throwable.toString() + " and clear cmd queue");
//                    }
//                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        logtest("我完成了2");//与 doOnComplete  调用时机是一样的。   方法中的所有的onComplete都调用
                    }
                });
    }
    public void logtest(String s){
        Log.i("logtest",s);

    }
    public void toash(String ss){
        Toast.makeText(this,ss,Toast.LENGTH_SHORT).show();
    }
    public Observable<Integer> text1(int a,int b,final String yuju){

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int abc = a+b;
                e.onNext(abc);
                e.onComplete();
               // e.onError(new Throwable("cuolema"));
            }
        });
    }

    public Observable<String> speak(String speak){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(speak);
                //Thread.sleep(2000);
                e.onComplete();
            }
        })
        .timeout(1000, TimeUnit.MILLISECONDS, Observable.error(new Throwable("超时了")));//没有onComplete抛异常
//        .timeout(1000, TimeUnit.MILLISECONDS, new Observable<String>() {
//            @Override
//            protected void subscribeActual(Observer<? super String> observer) {
//
//            }
//        });
//                .timeout(5000, TimeUnit.MILLISECONDS, new ObservableSource<String>() {
//                    @Override
//                    public void subscribe(Observer<? super String> observer) {
//                        //只要某个地方调用了onComplete就表示结束了
//                        Log.e(TAG,"Send error : " + " and clear cmd queue");
//                    }
//                });
    }
    public  Observable<Object> onException() {
        Observable<Object> obs = Observable
                .create(sub -> {
                    for (int i = 0; i < 10; i++) {
                        if (i == 1) {
                            //抛出异常
                          //  return Observable.error(new Throwable("Device is offline"));
                            sub.onError(new RuntimeException("error"));
                        }
                        sub.onNext(i);
                        //sub.onComplete();
                    }
                });
        obs.onExceptionResumeNext(Observable.just("new resumed observable"))
                .subscribe(obj -> System.out.println(obj));
      return obs;
    }
    //根据次数来判断是否重试
    public Observable<Object> retry() {
        Observable<Object> obs = Observable
                .create(sub -> {
                    for (int i = 0; i < 10; i++) {
                        if (i == 1) {
                            sub.onError(new RuntimeException("error"));
                        }
                        sub.onNext(i);
                    }
                });
        obs.retry((time,ex)->{
            if(time==2 && ex instanceof RuntimeException){
                return false;
            }
            return true;
        }).subscribe(obj -> System.out.println(obj), throwable -> logtest("cuowu"));
        return obs;
    }
}
