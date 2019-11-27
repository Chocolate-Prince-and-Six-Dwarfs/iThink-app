package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.model.http.ApiHelper;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ApiHelper apiHelper;
    private EditText editText1;
    private EditText editText2;

    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        editText1 = (EditText)findViewById(R.id.et_user_name);
        editText2 = (EditText)findViewById(R.id.et_psw);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                RequestBody requestBody = new FormBody.Builder()
                    .add("email", editText1.getText().toString())
                    .add("pwd",editText2.getText().toString())
                    .build();
                ApiHelper.sendHttpRequest("http://47.97.187.33:8080/user/login",requestBody);
                break;
            case R.id.btn_enter:
                Intent intent = new Intent("android.intent.action.SECOND");
                startActivity(intent);
            default:
                break;

        }
    }



}
