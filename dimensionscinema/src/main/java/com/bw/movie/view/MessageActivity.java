package com.bw.movie.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.MyApp;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bw.movie.adapter.MessageAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.MessageList;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.ChangeSysMsgStatusPresenter;
import com.bw.movie.presenter.FindUnreadMessageCountPresenter;
import com.bw.movie.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.message_recy)
    RecyclerView messageRecy;
    @BindView(R.id.img_finnish)
    ImageView imgFinnish;
    @BindView(R.id.weidu)
    TextView weudu;
    private MessageAdapter messageAdapter;
    private int position;
    // 未读列表
    private MessagePresenter messagePresenter;
    private ChangeSysMsgStatusPresenter changeSysMsgStatusPresenter;
    private FindUnreadMessageCountPresenter findUnreadMessageCountPresenter;
    private List<AllUser> users = new ArrayList<>();
    private AllUserDao allUserDao;
    private int userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //查询数据库
        allUserDao = MyApp.daoSession.getAllUserDao();
        List<AllUser> allUsers = allUserDao.loadAll();
        users.addAll(allUsers);
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        messageAdapter = new MessageAdapter(this);
        messageRecy.setAdapter(messageAdapter);
        messageRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 查询消息列表
        messagePresenter = new MessagePresenter(new MessageCall());
        messagePresenter.request(userId, sessionId, 1, 10);
        // 改变状态
        changeSysMsgStatusPresenter = new ChangeSysMsgStatusPresenter(new Change());
        // 查询未读条数
        findUnreadMessageCountPresenter = new FindUnreadMessageCountPresenter(new Find());
        messageAdapter.setOnHideText(new MessageAdapter.OnHideText() {
            @Override
            public void onHideText(int id, int i, MessageList messageList) {
                position = i;
                changeSysMsgStatusPresenter.request(userId, sessionId, id, messageList);
            }
        });
        findUnreadMessageCountPresenter.request(userId,sessionId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findUnreadMessageCountPresenter.request(userId,sessionId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findUnreadMessageCountPresenter.request(userId,sessionId);
    }

    @OnClick(R.id.img_finnish)
    public void onViewClicked() {
        finish();
    }

    class MessageCall implements DataCall<Result<List<MessageList>>> {

        @Override
        public void success(Result<List<MessageList>> data) {
            if (data.getStatus().equals("0000")) {
                messageAdapter.addAll(data.getResult());
                messageAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }


    private class Change implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                MessageList o = (MessageList) data.getArgs()[3];
                o.setStatus(1);
                findUnreadMessageCountPresenter.request(userId,sessionId);
                messageAdapter.notifyItemChanged(position);
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    private class Find implements DataCall<Result> {
        @Override
        public void success(Result data) {
            weudu.setText("("+data.getCount()+"条未读)");
        }

        @Override
        public void fail(ApiException a) {

        }
    }
}
