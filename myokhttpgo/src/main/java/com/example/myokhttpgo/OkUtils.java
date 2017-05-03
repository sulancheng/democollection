package com.example.myokhttpgo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.myokhttpgo.bean.Login;
import com.example.myokhttpgo.bean.RequestEntity;
import com.example.myokhttpgo.bean.ResponseEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.StringCallback;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;

import dolphin.tools.util.CompressUtil;
import dolphin.tools.util.LogUtil;
import dolphin.tools.util.StringUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by su
 * on 2017/3/24.
 */
public class OkUtils {
    public static boolean RELEASE = true;
    private static final String ADDRESS_1 = "http://inside.purifit.fitband.tech";//debug service
    //private static final String ADDRESS_2 = "http://www.hchlnet.com"; //release service
    private static final String ADDRESS_2 = "https://api.vfit.fitband.tech"; //release service
    private static final String SERVICE = "/Service";

    private static String getServerChannel(Context context) {
        String serverUrl = null;
        if (RELEASE) {
            serverUrl = ADDRESS_2 + SERVICE;
        } else {
            serverUrl = ADDRESS_1 + SERVICE;
        }
        return serverUrl;
    }

    public static void login1(Context context, Login param, MyResponse myresponse) {

        executeCommon(context, getServerChannel(context) + "/userinfoDatas/loginAll", param, myresponse);
    }

    public static void executeCommon(Context context, String url, Object param, MyResponse myresponse) {
        executeCommon(context, url, param, "001", myresponse);
    }

    public static void executeCommon(Context context, String url, Object param, String manufCode, MyResponse myresponse) {
        // 打包网络包
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setSysdata(RequestEntity.generateSysData(context));
        requestEntity.getSysdata().setManufCode(manufCode);
        String str = JSON.toJSONString(param);
        LogUtil.i("发送数据1(http),url=" + url + " : " + manufCode);
        requestEntity.setRqdata(new String(Base64.encodeBase64(CompressUtil.gZip(StringEscapeUtils
                .escapeJavaScript(str).getBytes()))));
        RequestEntity req = requestEntity;
        requsetJson(url, context, JSON.toJSONString(req), myresponse);
    }


    public static void norRequest(String url, Context mcontext) {
        OkGo.get(url)     // 请求方式和请求url
                .tag(mcontext)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("norRequest", " s = " + s + " call = " + call + "Respone = " + response.message());
                        // s 即为所需要的结果
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Log.e("norRequestonError", " Exception = " + e.getMessage() + "  response =" + response + " call=" + call.request());
                    }
                });
    }

    public static void requestBitmap(String url, Context mcontext) {
        OkGo.get(url)//
                .tag(mcontext)//
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        // bitmap 即为返回的图片数据
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
    static MyResponse re;
    public static void requsetJson(String url, Context mcontext, String json, MyResponse myresponse) {
        re = myresponse;
        OkGo.post(url)//
                .tag(mcontext)//
//	.params("param1", "paramValue1")//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
                .upJson(json)//setCertificates
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("requsetJson", " s = " + s + " call = " + call + "Respone = " + response.message());
                        checkresponse(s, re);
                        // s 即为所需要的结果
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Log.e("requsetJsonError", " Exception = " + e.getMessage() + "  response =" + response + " call=" + call.request());
                    }
                });
    }

    public interface MyResponse {
        void expResponse(ResponseEntity myresponse);
    }

    private static void checkresponse(String responseBody, MyResponse myresponse) {
        if (!StringUtil.isBlank(responseBody)) {
            String result = new String(StringEscapeUtils
                    .unescapeJavaScript(new String(CompressUtil
                            .unGZip(Base64.decodeBase64(responseBody.getBytes())))));
            LogUtil.i1(result);
//            try {
//                JSONObject jsonObject = new JSONObject(result);
//                String results = jsonObject.getString("results");
//            }catch (Exception ex){
//
//            }
            ResponseEntity responseEntity = JSON.parseObject(result, ResponseEntity.class);
            Log.i("responseEntity", responseEntity.toString());
            if (myresponse != null) {
                myresponse.expResponse(responseEntity);
            }
            if ("001".equals(responseEntity.getStateCode())) {
               // Log.i("checkresponse", " s = " + responseEntity.getData() + "message = " + responseEntity.getMsg());
            } else {

            }
        } else {
            try {
                throw new Throwable("服务器返回错误的参数");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
