package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class IdeadetailActivity extends AppCompatActivity {

    private TextView owner;
    private TextView title;
    private TextView content;
    private TextView date;


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
    }
}
