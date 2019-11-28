package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.model.http.ApiHelper;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editText1;
    private EditText editText2;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        editText1 = (EditText)findViewById(R.id.et_user_name);
        editText2 = (EditText)findViewById(R.id.et_psw);
        checkBox = (CheckBox)findViewById(R.id.check_box);

        //检查是否保存过数据
        SharedPreferences sharedPreferences=getSharedPreferences("config",0);
        String username=sharedPreferences.getString("name","");
        String password=sharedPreferences.getString("password","");
        boolean checkbox=sharedPreferences.getBoolean("checkbox",false);

        editText1.setText(username);
        editText2.setText(password);
        checkBox.setChecked(checkbox);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //登录按钮
            case R.id.btn_login:
                String username = editText1.getText().toString();
                String password = editText2.getText().toString();


                if(TextUtils.isEmpty(username) ||TextUtils.isEmpty(password)){               //检验账号密码是否为空
                    Toast.makeText(MainActivity.this,"账号密码不能为空！",Toast.LENGTH_SHORT).show();

                }else{
                    if(checkBox.isChecked()){                           //检查是否需要记住密码
                        SharedPreferences sharedPreferences = getSharedPreferences("config",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        //把数据进行保存
                        editor.putString("name",username);
                        editor.putString("password",password);
                        //记住勾选的状态
                        editor.putBoolean("checkbox",checkBox.isChecked());
                        //提交数据
                        editor.commit();
                    }
                    RequestBody requestBody = new FormBody.Builder()
                            .add("email", username)
                            .add("pwd",password)
                            .build();
                    ApiHelper.sendHttpRequest("http://47.97.187.33:8080/user/login",requestBody);
                }

                break;
                //直接进入按钮
            case R.id.btn_enter:
                Intent intent = new Intent("android.intent.action.SECOND");
                startActivity(intent);
            default:
                break;

        }
    }



}
