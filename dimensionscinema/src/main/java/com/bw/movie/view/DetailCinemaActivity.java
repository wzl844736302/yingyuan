package com.bw.movie.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class DetailCinemaActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cinema);
        //接收值
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }
    @Override
    public float getSizeInDp() {
        return 720;
    }
}
