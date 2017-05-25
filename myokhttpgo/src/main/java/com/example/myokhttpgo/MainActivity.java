package com.example.myokhttpgo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myokhttpgo.bean.Login;
import com.example.myokhttpgo.bean.ResponseEntity;

import java.security.MessageDigest;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //OkUtils.norRequest("http://gank.io/api/data/福利/10/1",this);
        Login login = new Login();
        login.setUsername("247175121@qq.com");
        //login.setPasswd("test");
        try {
            login.setPasswd(encrypt("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkUtils.login1(this, login, new OkUtils.MyResponse() {
            @Override
            public void expResponse(ResponseEntity myresponse) {
                if ("001".equals(myresponse.getStateCode())) {
                    Toast.makeText(MainActivity.this,"登录成功！！！",Toast.LENGTH_LONG).show();
                    Log.i("checkresponse", " s = " + myresponse.getData() + "message = " + myresponse.getMsg());
                } else {
                    Toast.makeText(MainActivity.this,"登录失败！！！",Toast.LENGTH_LONG).show();
                }
            }
        });
        OkUtils.login1(this,login,(a)->{

        });

//        Map<String,String> testmap = new HashMap<>();
//        new JSONObject(testmap);
//        JSON.parseObject()
    }
    public static String encrypt(String password) throws Exception {
        if (password == null || "".equals(password))
            return null;
        byte[] buf = password.getBytes();
        MessageDigest algorithm = null;
//		try {

        algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(buf);
        byte[] digest1 = algorithm.digest();
        int[] digest1_int = new int[digest1.length];
        for (int i = 0; i < digest1.length; i++)
            digest1_int[i] = (int) (digest1[i] & 0xFF);
        StringBuffer SB1 = new StringBuffer("");
        for (int i = 0; i < digest1_int.length; i++)
            SB1.append(digest1_int[i]);
        return SB1.toString();
    }

}
