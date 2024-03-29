package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.model.http.ApiHelper;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editText1;
    private EditText editText2;
    private CheckBox checkBox;

    private String userId;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCollector.addActivity(this);

        Button btn_login = (Button)findViewById(R.id.btn_login);
        Button btn_enter = (Button)findViewById(R.id.btn_enter);
        btn_login.setOnClickListener(this);
        btn_enter.setOnClickListener(this);

        editText1 = (EditText)findViewById(R.id.et_user_name);
        editText2 = (EditText)findViewById(R.id.et_psw);
        checkBox = (CheckBox)findViewById(R.id.check_box);

        //检查是否保存过数据
        SharedPreferences sharedPreferences=getSharedPreferences("config",0);
        String username=sharedPreferences.getString("name","");
        String password=sharedPreferences.getString("password","");
        boolean checkbox=sharedPreferences.getBoolean("checkbox",false);

        flag = false;

        userId = sharedPreferences.getString("userId","");
        Log.d("测试中：","什么玩意？？？"+userId);
        if(!userId.isEmpty()){
            Intent intent = new Intent(MainActivity.this, IdeasActivity.class);
            intent.putExtra("user_id",userId);
            startActivity(intent);
            finish();
        }

        editText1.setText(username);
        editText2.setText(password);
        checkBox.setChecked(checkbox);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //登录按钮
            case R.id.btn_login:

                HttpRequest();
                break;
            //直接进入按钮
            case R.id.btn_enter:

                //Toast.makeText(MainActivity.this,"直接进入",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, IdeasActivity.class);
                intent.putExtra("user_id","");
                startActivity(intent);
                MainActivity.this.finish();
                break;

            default:
                break;
        }

    }

    public void HttpRequest(){
        String username = editText1.getText().toString();
        String password = editText2.getText().toString();

        if(TextUtils.isEmpty(username) ||TextUtils.isEmpty(password)){               //检验账号密码是否为空
            Toast.makeText(MainActivity.this,"账号密码不能为空！",Toast.LENGTH_SHORT).show();

        }else{

            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Login...");
            if (!MainActivity.this.isFinishing())
            {
                progressDialog.show();
            }


            RequestBody requestBody = new FormBody.Builder()
                    .add("email", username)
                    .add("pwd",password)
                    .build();
            ApiHelper.sendHttpRequest("http://47.97.187.33:8080/user/login", requestBody ,new okhttp3.Callback(){
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String responseData = response.body().string();
                    //解析返回结果，进行判断是否是正确账号密码
                    if(1 == ApiHelper.flagJSONWithGSON(responseData)){

                        String cookie = response.header("Set-Cookie");

                        ApiHelper.sendcookieRequest("http://47.97.187.33:8080/user/getLoginId",cookie,new okhttp3.Callback(){
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                userId = response.body().string();
                                flag = true;
                            }
                        });

                        //检查是否需要记住密码
                        if(checkBox.isChecked()){
                            SharedPreferences sharedPreferences = getSharedPreferences("config",0);
                            SharedPreferences.Editor editor=sharedPreferences.edit();


                            //把数据进行保存
                            editor.putString("name",username);
                            editor.putString("password",password);
                            //记住勾选的状态
                            editor.putBoolean("checkbox",checkBox.isChecked());

                            //保存用户cookie
                            editor.putString("userId",userId);

                            //提交数据
                            editor.commit();

                        }else{
                            SharedPreferences sharedPreferences = getSharedPreferences("config",0);
                            SharedPreferences.Editor editor=sharedPreferences.edit();

                            //把数据进行保存
                            editor.remove("checkbox");
                            editor.remove("password");
                            editor.remove("userId");
                            editor.putString("name",username);

                            //提交数据
                            editor.commit();
                        }
                        if (!MainActivity.this.isFinishing())
                        {
                            while(false == flag){}
                            progressDialog.dismiss();
                        }
                        Intent intent = new Intent(MainActivity.this, IdeasActivity.class);

                        intent.putExtra("user_id",userId);
                        startActivity(intent);

                        finish();

                    }else{

                        if (!MainActivity.this.isFinishing())
                        {
                            progressDialog.dismiss();
                        }
                        Looper.prepare();
                        Toast.makeText(getBaseContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                }
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("MainActivity","发送请求失败");
                }
            });
        }
    }



}
