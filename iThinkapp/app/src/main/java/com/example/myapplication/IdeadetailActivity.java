package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import com.example.myapplication.model.http.ApiHelper;
import com.example.myapplication.model.pojo.Idea;
import com.example.myapplication.model.pojo.User;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IdeadetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView owner;
    private TextView title;
    private TextView content;
    private TextView date;

    private User user;
    private Idea idea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideadetail);

        idea = new Idea();

        owner = (TextView) findViewById(R.id.detail_owner);
        title = (TextView) findViewById(R.id.detail_title);
        content = (TextView) findViewById(R.id.detail_content);


        date = (TextView) findViewById(R.id.detail_date);

        Intent intent = getIntent();
        user = (User) getIntent().getSerializableExtra("user");
        idea = (Idea) getIntent().getSerializableExtra("idea");

        title.setText(idea.getTitle());
        content.setText(idea.getContent());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        date.setText(simpleDateFormat.format(idea.getDate()));
        owner.setText(idea.getOwnerName());


        Button button1 = (Button)findViewById(R.id.button_backward);
        Button button2 = (Button)findViewById(R.id.button_idea);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_backward:
                finish();
                break;
            case R.id.button_idea:
                showMenu(v);
                break;
            default:
                break;

        }
    }

    //右上角管理菜单啊
    private void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(IdeadetailActivity.this,view);

        popupMenu.getMenuInflater().inflate(R.menu.idea_menu,popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.upload_idea:
                        RequestBody requestBody = new FormBody.Builder()

                                .add("name",title.getText().toString())
                                .add("content",content.getText().toString())
                                .add("userId",String.valueOf(user.getId()))
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
                    case R.id.delete_idea:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(IdeadetailActivity.this);
                        dialog.setTitle("确定删除此条胶囊？");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataSupport.deleteAll(Idea.class,"ideaId = ?",String.valueOf(idea.getIdeaId()));
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

                    case R.id.save_idea:
                        idea.setDate(new Date());
                        idea.setContent(content.getText().toString());
                        idea.updateAll("ideaId = ? and ownerId = ?",String.valueOf(idea.getIdeaId()),String.valueOf(idea.getOwnerId()));
                        Toast.makeText(IdeadetailActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }
}
