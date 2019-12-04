package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.model.http.ApiHelper;
import com.example.myapplication.model.pojo.Idea;
import com.example.myapplication.model.pojo.User;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateIdeaActivity extends AppCompatActivity implements View.OnClickListener {

    private Idea idea;
    EditText editTitle;
    EditText editContent;

    String user_id;
    private User user;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ActivityCollector.addActivity(this);

        Button button = (Button)findViewById(R.id.create_idea);
        editTitle =  (EditText)findViewById(R.id.create_title);
        editContent = (EditText)findViewById(R.id.create_content);
        //接收传过来的用户名
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        if(user_id.isEmpty()){
            initUser();
        }else{
            getUserInfo(user_id);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_idea:
                    Date date = new Date();
                idea.setIdeaId(0);
                if(user_id.isEmpty())
                {
                    idea.setOwnerId(0);
                    idea.setOwnerName("游客");
                }
                else{
                    idea.setOwnerId(Integer.parseInt(user_id));
                    idea.setOwnerName(user.getName());
                }
                idea.setTitle(editTitle.toString());
                idea.setContent(editContent.toString());
                idea.setDate(date);
                idea.setIs_upload(false);
                idea.save();
                break;
                default:
                    break;
        }
    }

    public void getUserInfo(String user_id){

        RequestBody requestBody = new FormBody.Builder()
                .add("id", user_id)
                .build();

        ApiHelper.sendHttpRequest("http://47.97.187.33:8080/user/getById",requestBody ,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String userData = response.body().string();
                user = ApiHelper.userJSONWithGSON(userData);
                flag = true;
            }
        });
    }

    public void initUser()
    {
        user.setId(0);
        user.setName("游客");
    }
}
