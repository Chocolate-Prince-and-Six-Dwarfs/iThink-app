package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.http.ApiHelper;
import com.example.myapplication.model.pojo.Idea;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IdeadetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView owner;
    private TextView title;
    private TextView content;
    private TextView date;

    private String ideal_id;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideadetail);

        owner = (TextView) findViewById(R.id.detail_owner);
        title = (TextView) findViewById(R.id.detail_title);
        content = (TextView) findViewById(R.id.detail_content);
        date = (TextView) findViewById(R.id.detail_date);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
        date.setText(intent.getStringExtra("date"));
        owner.setText(intent.getStringExtra("user_name"));
        ideal_id = intent.getStringExtra("ideal_id");
        user_id = intent.getStringExtra("user_id");

        Button button1 = (Button)findViewById(R.id.upload);
        Button button2 = (Button)findViewById(R.id.delete);
        Log.d("测试结果：",String.valueOf(ideal_id));
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload:
                RequestBody requestBody = new FormBody.Builder()
                        .add("name",title.getText().toString())
                        .add("content",content.getText().toString())
                        .add("userId",String.valueOf(user_id))
                        .add("buildTime",date.getText().toString())
                        .build();

                ApiHelper.sendHttpRequest("http://47.97.187.33:8080/capsule/save",requestBody ,new okhttp3.Callback(){
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String userData = response.body().string();
                        if(!userData.isEmpty()){
                            Looper.prepare();
                            Toast.makeText(IdeadetailActivity.this,"上传成功，"+userData,Toast.LENGTH_SHORT).show();
                            Log.d("结果:",userData);
                            Looper.loop();
                        }else
                        {
                            Toast.makeText(IdeadetailActivity.this,"shibai",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(IdeadetailActivity.this);
                dialog.setTitle("确定删除此条胶囊？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Idea.class,"ideaId = ?",ideal_id);
                        Toast.makeText(IdeadetailActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                dialog.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

                break;
            default:
                break;

        }
    }
}
