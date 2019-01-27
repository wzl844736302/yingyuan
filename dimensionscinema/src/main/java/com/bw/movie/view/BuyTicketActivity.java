package com.bw.movie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.BuyTicketPresenter;

import java.util.ArrayList;
import java.util.List;

public class BuyTicketActivity extends AppCompatActivity {
    private BuyTicketPresenter ticketPresenter;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private RecyclerView mrecyclerbuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //初始化控件
        mrecyclerbuy = findViewById(R.id.mrecyclerBuy);

    }
}
