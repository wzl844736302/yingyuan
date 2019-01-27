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

import com.bw.movie.R;
import com.bw.movie.adapter.DetailCinemaAdapter;
import com.bw.movie.adapter.ScheduleAdapter;
import com.bw.movie.bean.MovieList;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.ScheduleList;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.DetailCinemaPresenter;
import com.bw.movie.presenter.SchedulePresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class DetailCinemaActivity extends WDActivity {

    private DetailCinemaPresenter cinemaPresenter;
    private SimpleDraweeView msim;
    private TextView mname;
    private TextView mweizhi;
    private RecyclerCoverFlow list;
    private DetailCinemaAdapter detailCinemaAdapter;
    private RecyclerView mrecycler;
    private SchedulePresenter schedulePresenter;
    private int id;
    private ScheduleAdapter scheduleAdapter;
    private List<MovieList> result;
    private int id1;
    private ImageView mreturn;

    @Override
    protected void initView() {
        //初始化控件
        msim = findViewById(R.id.msim);
        mname = findViewById(R.id.mname);
        mweizhi = findViewById(R.id.mweizhi);
        list = findViewById(R.id.list);
        mrecycler = findViewById(R.id.mrecycler);
        mreturn = findViewById(R.id.mreturn);
        //接收值
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String tupian = intent.getStringExtra("tupian");
        String name = intent.getStringExtra("name");
        String weizhi = intent.getStringExtra("weizhi");
        //设置值
        msim.setImageURI(tupian);
        mname.setText(name);
        mweizhi.setText(weizhi);
        //设置适配器
        detailCinemaAdapter = new DetailCinemaAdapter(this);
        list.setAdapter(detailCinemaAdapter);

        //获取数据
        cinemaPresenter = new DetailCinemaPresenter(new DetailCall());
        cinemaPresenter.request(id);
        //设置适配器
        scheduleAdapter = new ScheduleAdapter(DetailCinemaActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailCinemaActivity.this);
        mrecycler.setLayoutManager(layoutManager);
        mrecycler.setAdapter(scheduleAdapter);
        //点击按钮返回
        mreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        scheduleAdapter.setBacks(new ScheduleAdapter.CallBacks() {
            @Override
            public void call(int pos) {
                //跳转
                Intent intent = new Intent(DetailCinemaActivity.this, SeatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_cinema;
    }
    //实现当前电影列表
    class DetailCall implements DataCall<Result<List<MovieList>>> {
        @Override
        public void success(Result<List<MovieList>> data) {
            if (data.getStatus().equals("0000")) {
                result = data.getResult();
                detailCinemaAdapter.addAll(result);
                id1 = result.get(0).getId();
                list.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                    @Override
                    public void onItemSelected(int position) {
                        id1 = result.get(position).getId();
                        schedulePresenter = new SchedulePresenter(new ScheduleCall());
                        schedulePresenter.request(DetailCinemaActivity.this.id, id1);
                    }
                });
                //获取数据
                schedulePresenter = new SchedulePresenter(new ScheduleCall());
                schedulePresenter.request(id, id1);
                detailCinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //实现电影排期列表
    class ScheduleCall implements DataCall<Result<List<ScheduleList>>> {

        @Override
        public void success(Result<List<ScheduleList>> data) {
            if (data.getStatus().equals("0000")) {
                final List<ScheduleList> result = data.getResult();
                scheduleAdapter.addAll(result);
                scheduleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
