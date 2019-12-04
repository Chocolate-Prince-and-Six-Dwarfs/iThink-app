package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import com.example.myapplication.model.Adapter.IdeaAdapter;
import com.example.myapplication.model.http.ApiHelper;
import com.example.myapplication.model.pojo.Idea;
import com.example.myapplication.model.pojo.User;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IdeasActivity extends AppCompatActivity implements View.OnClickListener{

    /*private List<Idea> ideaList = new ArrayList<>();*/
    private List<Idea> ideaList;
    private String user_id;
    private User user;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas);

        ActivityCollector.addActivity(this);
        ideaList = DataSupport.findAll(Idea.class);

        IdeaAdapter adapter = new IdeaAdapter(IdeasActivity.this,R.layout.item_simple_idea, ideaList);

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        handler.postDelayed(task, 1000);

        //左上角登录按钮
        Button back_login = (Button)findViewById(R.id.button_back_login);
        back_login.setOnClickListener(this);

        //创建数据库
        LitePal.getDatabase();

        //接受传过来的用户名
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        flag = false;

        //判断是否登陆过，若登录了，按钮失效
        if(user_id.isEmpty()){
            back_login.setText("登录");
            user = new User();
        }else{
            getUserInfo(user_id);
            while(false == flag){}
            back_login.setText(user.getName());
            back_login.setEnabled(false);
        }

        //点击创意转到创意详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Idea idea = ideaList.get(i);
                        Intent intent1 = new Intent(IdeasActivity.this,IdeadetailActivity.class);

                        intent1.putExtra("idea",idea);
                        intent1.putExtra("user",user);
                        startActivity(intent1);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_back_login:
                Intent intent = new Intent(IdeasActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button_menu:
                showMenu(v);
                break;
            default:
                break;
        }
    }


    //右上角菜单
    private void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(IdeasActivity.this,view);

        popupMenu.getMenuInflater().inflate(R.menu.main,popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.login_out:
                        SharedPreferences.Editor editor = getSharedPreferences("config",0).edit();
                        editor.remove("userId");
                        editor.commit();
                        Intent intent = new Intent(IdeasActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.exit:
                        ActivityCollector.finishAll();
                        break;
                    case R.id.create_idea:
                        Intent intent1 = new Intent(IdeasActivity.this, CreateIdeaActivity.class);
                        intent1.putExtra("user_id",user_id);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

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

    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (true) {
                ideaList = DataSupport.findAll(Idea.class);

                IdeaAdapter adapter = new IdeaAdapter(IdeasActivity.this,R.layout.item_simple_idea, ideaList);

                ListView listView = (ListView)findViewById(R.id.list_view);
                listView.setAdapter(adapter);
                handler.postDelayed(this, 1000);
            }
        }
    };
}
