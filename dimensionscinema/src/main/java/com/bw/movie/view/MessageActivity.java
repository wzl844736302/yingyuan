package com.bw.movie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.MessageAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.MessageList;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private MessagePresenter messagePresenter;
    private RecyclerView mrecycler;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //初始化控件
        mrecycler = findViewById(R.id.mrecycler);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //设置适配器
        messageAdapter = new MessageAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrecycler.setLayoutManager(layoutManager);
        mrecycler.setAdapter(messageAdapter);
        //设置数据
        messagePresenter = new MessagePresenter(new MessageCall());
        messagePresenter.request(userId,sessionId,1,10);
    }
    class MessageCall implements DataCall<Result<List<MessageList>>>{

        @Override
        public void success(Result<List<MessageList>> data) {
            if (data.getStatus().equals("0000")){
                List<MessageList> result = data.getResult();
                messageAdapter.addAll(result);
                messageAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
