package com.bw.movie.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.ScheduleAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.MovieDetail;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.ScheduleList;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.DetailCinemaPresenter;
import com.bw.movie.presenter.DetailMoviePresenter;
import com.bw.movie.presenter.SchedulePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class TicketDetailActivity extends AppCompatActivity {

    private TextView tname,taddress;
    private DetailMoviePresenter moviePresenter;
    private int userId;
    private String sessionId;
    private List<AllUser> users = new ArrayList<>();
    private SimpleDraweeView msim;
    private TextView dname,leixing,daoyan,shichang,chandi;
    private SchedulePresenter cinemaPresenter;
    private ScheduleAdapter scheduleAdapter;
    private RecyclerView mrecycler;
    private ImageView fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        //初始化控件
        tname = findViewById(R.id.name);
        taddress = findViewById(R.id.address);
        msim = findViewById(R.id.msim);
        dname = findViewById(R.id.dname);
        leixing = findViewById(R.id.leixing);
        daoyan = findViewById(R.id.daoyan);
        shichang = findViewById(R.id.shichang);
        chandi = findViewById(R.id.chandi);
        mrecycler = findViewById(R.id.mrecycler);
        fanhui = findViewById(R.id.fanhui);
        //接收值
        Intent intent = getIntent();
        int id1 = intent.getIntExtra("id1", 0);
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        //设置值
        tname.setText(name);
        taddress.setText(address);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //获取数据
        moviePresenter = new DetailMoviePresenter(new MovieCall());
        moviePresenter.request(userId,sessionId,id);
        //设置适配器
        scheduleAdapter = new ScheduleAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrecycler.setLayoutManager(layoutManager);
        mrecycler.setAdapter(scheduleAdapter);
        //获取数据
        cinemaPresenter = new SchedulePresenter(new SchedulesCall());
        cinemaPresenter.request(id1,id);
        //点击返回
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //实现电影排期列表
    class SchedulesCall implements DataCall<Result<List<ScheduleList>>>{

        @Override
        public void success(Result<List<ScheduleList>> data) {
            if (data.getStatus().equals("0000")){
                List<ScheduleList> result = data.getResult();
                scheduleAdapter.addAll(result);
                scheduleAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //实现详情接口
    class MovieCall implements DataCall<Result<MovieDetail>>{
        @Override
        public void success(Result<MovieDetail> data) {
            if (data.getStatus().equals("0000")){
                MovieDetail result = data.getResult();
                msim.setImageURI(result.getImageUrl());
                dname.setText(result.getName());
                leixing.setText(result.getMovieTypes());
                daoyan.setText(result.getDirector());
                shichang.setText(result.getDuration());
                chandi.setText(result.getPlaceOrigin());
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
}
