package com.bw.movie.view;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.FocusOnAdapter;
import com.bw.movie.adapter.FocusOnAdapter1;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.FocusList;
import com.bw.movie.bean.MovieList;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.MovieListPresenter;
import com.bw.movie.presenter.QureyFocusPresenter;
import com.bw.movie.utils.jilei.WDActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class FocusOnActivity extends AppCompatActivity {
    private RadioGroup mradio_cinema;
    private Button mbutton1;
    private Button mbutton2;
    private RecyclerView recyclerfocus;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private QureyFocusPresenter qureyFocusPresenter;
    private FocusOnAdapter focusOnAdapter;
    private MovieListPresenter movieListPresenter;
    private FocusOnAdapter1 focusOnAdapter1;
    private ImageView mreturn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_focus_on);
        //初始化控件
        mradio_cinema = findViewById(R.id.mRadio_cinema);
        mbutton1 = findViewById(R.id.mbutton1);
        mbutton2 = findViewById(R.id.mbutton2);
        recyclerfocus = findViewById(R.id.recyclerFocus);
        mreturn = findViewById(R.id.mreturn);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //设置适配器
        focusOnAdapter1 = new FocusOnAdapter1(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        recyclerfocus.setLayoutManager(layoutManager1);
        recyclerfocus.setAdapter(focusOnAdapter1);
        movieListPresenter = new MovieListPresenter(new MovieListCall());
        movieListPresenter.request(userId,sessionId,1,20);

        //默认选中第一个
        mradio_cinema.check(mradio_cinema.getChildAt(0).getId());
        mbutton1.setTextColor(Color.WHITE);
        mradio_cinema.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mbutton1:
                        mbutton1.setTextColor(Color.WHITE);
                        mbutton2.setTextColor(Color.BLACK);
                        //设置适配器
                        focusOnAdapter1 = new FocusOnAdapter1(FocusOnActivity.this);
                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(FocusOnActivity.this);
                        recyclerfocus.setLayoutManager(layoutManager1);
                        recyclerfocus.setAdapter(focusOnAdapter1);
                        movieListPresenter = new MovieListPresenter(new MovieListCall());
                        movieListPresenter.request(userId,sessionId,1,20);
                        break;
                    case R.id.mbutton2:
                        mbutton1.setTextColor(Color.BLACK);
                        mbutton2.setTextColor(Color.WHITE);
                        //设置适配器
                        focusOnAdapter = new FocusOnAdapter(FocusOnActivity.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(FocusOnActivity.this);
                        recyclerfocus.setLayoutManager(layoutManager);
                        recyclerfocus.setAdapter(focusOnAdapter);
                        qureyFocusPresenter = new QureyFocusPresenter(new QureyFocusCall());
                        qureyFocusPresenter.request(userId,sessionId,1,20);
                        break;
                }
            }
        });
        //点击返回
        mreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //实现关注电影的接口
    class MovieListCall implements DataCall<Result<List<MovieList>>>{
        @Override
        public void success(Result<List<MovieList>> data) {
            if (data.getStatus().equals("0000")){
                List<MovieList> result = data.getResult();
                focusOnAdapter1.addAll(result);
                focusOnAdapter1.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //实现关注影院的接口
    class QureyFocusCall implements DataCall<Result<List<FocusList>>>{

        @Override
        public void success(Result<List<FocusList>> data) {
            if (data.getStatus().equals("0000")){
                List<FocusList> result = data.getResult();
                focusOnAdapter.addAll(result);
                focusOnAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
}
