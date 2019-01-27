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
import com.bw.movie.adapter.TicketAdapter;
import com.bw.movie.bean.CinemasList;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.CinemasPresenter;
import com.bw.movie.utils.jilei.WDActivity;

import java.util.List;

public class TicketActivity extends WDActivity {
    private CinemasPresenter cinemasPresenter;
    private TextView mname;
    private RecyclerView recycler;
    private TicketAdapter ticketAdapter;
    private ImageView mreturn;
    private List<CinemasList> result;
    private int id;

    @Override
    protected void initView() {
        //初始化控件
        mname = findViewById(R.id.mName);
        recycler = findViewById(R.id.recycler);
        mreturn = findViewById(R.id.mreturn);
        //接收值
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        id = intent.getIntExtra("id", 0);
        mname.setText(name);
        //设置适配器
        ticketAdapter = new TicketAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(ticketAdapter);
        ticketAdapter.setsHuiDiao(new TicketAdapter.HuiDiao() {
            @Override
            public void huidiao(int pos,int id1) {
                CinemasList cinemasList = result.get(pos);
                String name = cinemasList.getName();
                String address = cinemasList.getAddress();
                Intent intent = new Intent(TicketActivity.this, TicketDetailActivity.class);
                intent.putExtra("id1",id1);
                intent.putExtra("id",id);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });
        //获取数据
        cinemasPresenter = new CinemasPresenter(new CinemasCall());
        cinemasPresenter.request(id);
        //点击返回
        mreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket;
    }
    //实现根据电影ID查询当前排片该电影的影院列表
    class CinemasCall implements DataCall<Result<List<CinemasList>>> {
        @Override
        public void success(Result<List<CinemasList>> data) {
            if (data.getStatus().equals("0000")) {
                result = data.getResult();
                ticketAdapter.addAll(result);
                ticketAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
