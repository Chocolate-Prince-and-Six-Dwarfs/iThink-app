package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.myapplication.model.Adapter.IdeaAdapter;
import com.example.myapplication.model.pojo.Idea;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class IdeasActivity extends AppCompatActivity implements View.OnClickListener{

    /*private List<Idea> ideaList = new ArrayList<>();*/
    private List<Idea> ideaList = DataSupport.findAll(Idea.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas);

        ActivityCollector.addActivity(this);

        initIdeas();
        IdeaAdapter adapter = new IdeaAdapter(IdeasActivity.this,R.layout.item_simple_idea, ideaList);

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //左上角登录按钮
        Button back_login = (Button)findViewById(R.id.button_back_login);
        back_login.setOnClickListener(this);

        //创建数据库
        LitePal.getDatabase();

        //接受传过来的用户名
        Intent intent = getIntent();
        String data = intent.getStringExtra("username");
        Log.d("IdeasActivity",data);

        //判断是否登陆过，若登录了，按钮失效
        if(data.isEmpty()){
            back_login.setText("登录");
        }else{
            back_login.setText(data);
            back_login.setEnabled(false);
        }

        //点击创意转到创意详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(IdeasActivity.this,"第"+i+"个item",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(IdeasActivity.this,CreateIdeaActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Toast.makeText(IdeasActivity.this,"第"+i+"个item",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(IdeasActivity.this,"第"+i+"个item",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void initIdeas(){

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
                        Intent intent = new Intent(IdeasActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.exit:
                        ActivityCollector.finishAll();
                        break;
                    case R.id.test1:
                        Idea idea1 = new Idea();
                        idea1.setIdeaId(1);
                        idea1.setTitle("第一个");
                        idea1.setContent("内容呀呀呀呀呀呀呀呀!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        idea1.setIs_upload(false);
                        idea1.save();
                        break;
                    case R.id.test2:
                        DataSupport.deleteAll(Idea.class,"id = ?","1");
                    default:
                        break;
                }
                return false;
            }
        });

    }
}
