package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.model.Adapter.IdeaAdapter;
import com.example.myapplication.model.pojo.Idea;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private List<Idea> ideaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas);
        initIdeas();
        IdeaAdapter adapter = new IdeaAdapter(SecondActivity.this,R.layout.item_simple_idea, ideaList);

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
    private void initIdeas(){
        Idea idea1 = new Idea(1,"This is first idea","FFFFFFFFFFFFFFirst idea");
        ideaList.add(idea1);
        Idea idea2 = new Idea(2,"This is second idea","SSSSSSSSSSSSecond idea");
        ideaList.add(idea2);
        Idea idea3 = new Idea(3,"This is third idea","TTTTTTTTTTTTThird idea");
        ideaList.add(idea3);
    }
}
