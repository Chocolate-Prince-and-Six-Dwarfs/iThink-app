package com.example.myapplication.model.http;

import com.example.myapplication.model.pojo.LoginFlag;
import com.example.myapplication.model.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiHelper {


    //判断登录状态
    public static int flagJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        LoginFlag loginFlag = gson.fromJson(jsonData,LoginFlag.class);
        return loginFlag.getStatus();
    }

    //获得用户信息
    public static User userJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData,User.class);
        return user;
    }


    //发送登陆请求
    public static void sendHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        //将要发送的数据用RequestBody对象保存，然后传入到该参数即可
        client.newCall(request).enqueue(callback);

    }

    //cookie
    public static void sendcookieRequest(String address, String cookie,okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .addHeader("cookie",cookie)
                .build();
        //将要发送的数据用RequestBody对象保存，然后传入到该参数即可
        client.newCall(request).enqueue(callback);

    }
}
