package com.bw.movie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.FeedBackPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends AppCompatActivity {
    @BindView(R.id.mll)
    LinearLayout mll;
    @BindView(R.id.tijiao)
    Button tijiao;
    private EditText medit;
    private FeedBackPresenter feedBackPresenter;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private AllUser allUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_feed_back);
        //绑定
        ButterKnife.bind(this);
        //初始化组件
        medit = findViewById(R.id.medit);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size() > 0) {
            allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }

    }
    //点击提交
    @OnClick(R.id.tijiao)
    public void tijiao(){
        String trim = medit.getText().toString().trim();
        feedBackPresenter = new FeedBackPresenter(new FeedBack());
        feedBackPresenter.request(userId,sessionId,trim);
    }
    //实现意见反馈接口
    class FeedBack implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(FeedBackActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //点击返回
    @OnClick(R.id.mreturn)
    public void mreturn(){
        finish();
    }
}
