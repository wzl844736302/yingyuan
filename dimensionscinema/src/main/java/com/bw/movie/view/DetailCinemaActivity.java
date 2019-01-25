package com.bw.movie.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.MovieList;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.DetailCinemaPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class DetailCinemaActivity extends AppCompatActivity implements CustomAdapt {

    private DetailCinemaPresenter cinemaPresenter;
    private SimpleDraweeView msim;
    private TextView mname;
    private TextView mweizhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cinema);
        //初始化控件
        msim = findViewById(R.id.msim);
        mname = findViewById(R.id.mname);
        mweizhi = findViewById(R.id.mweizhi);
        //接收值
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String tupian = intent.getStringExtra("tupian");
        String name = intent.getStringExtra("name");
        String weizhi = intent.getStringExtra("weizhi");
        //设置值
        msim.setImageURI(tupian);
        mname.setText(name);
        mweizhi.setText(weizhi);
        //设置数据
        cinemaPresenter = new DetailCinemaPresenter(new DetailCall());
        cinemaPresenter.request(id);
    }
    //实现当前电影列表
    class DetailCall implements DataCall<Result<List<MovieList>>>{
        @Override
        public void success(Result<List<MovieList>> data) {
            if (data.getStatus().equals("0000")){
                List<MovieList> result = data.getResult();
                Toast.makeText(DetailCinemaActivity.this, ""+result.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
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
