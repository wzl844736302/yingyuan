package com.bw.movie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends AppCompatActivity {
    @BindView(R.id.mll)
    LinearLayout mll;
    @BindView(R.id.tijiao)
    Button tijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        //绑定
        ButterKnife.bind(this);
    }
    //点击提交
    @OnClick(R.id.tijiao)
    public void tijiao(){

    }
    //点击返回
    @OnClick(R.id.mreturn)
    public void mreturn(){
        finish();
    }
}
