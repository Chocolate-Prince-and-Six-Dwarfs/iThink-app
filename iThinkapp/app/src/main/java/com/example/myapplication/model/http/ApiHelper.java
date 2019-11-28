package com.example.myapplication.model.http;

import android.util.Log;

import com.example.myapplication.model.pojo.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiHelper {

    public int parseJSONWithGSON(String jsonData){

        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<App>(){}.getType());

        App app = appList.get(0);

        return app.getFlag();
    }


    public static void sendHttpRequest(String address, RequestBody requestBody){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(address)
                            .post(requestBody)
                            .build();
                    //将要发送的数据用RequestBody对象保存，然后传入到该参数即可
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("MainActivity",responseData);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
