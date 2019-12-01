package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.example.myapplication.R;

import java.util.concurrent.CopyOnWriteArraySet;

public class TitleActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleText;
    private Button backwardbButton;
    private FrameLayout mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpView();
    }

    //初始化控件
    private void setUpView(){
        setContentView(R.layout.title_activity);
        titleText = (TextView)findViewById(R.id.idea_title);
        backwardbButton = (Button)findViewById(R.id.button_backward);
        backwardbButton.setOnClickListener(this);
    }

    protected void hideBackwardView(boolean hide){
        if(backwardbButton != null){
            if(hide) {
                backwardbButton.setVisibility(View.INVISIBLE);
            }
            else {
                backwardbButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_backward:
                onBackward(view);
                break;

            default:
                break;
        }
    }

    protected void onBackward(View backwardView){
        Toast.makeText(this,"点击返回",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }
}
