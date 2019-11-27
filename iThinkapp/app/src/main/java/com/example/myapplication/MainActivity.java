package com.example.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.http.ApiHelper;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        Button btn_enter = (Button)findViewById(R.id.btn_enter);
        btn_login.setOnClickListener(this);
        btn_enter.setOnClickListener(this);

        editText1 = (EditText)findViewById(R.id.et_user_name);
        editText2 = (EditText)findViewById(R.id.et_psw);
        Log.d("MainActivity","1");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.btn_login:
                intent.setData(Uri.parse("http://47.97.187.33:8080"));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;
            case R.id.btn_enter:
                Toast.makeText(MainActivity.this,"直接进入",Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
