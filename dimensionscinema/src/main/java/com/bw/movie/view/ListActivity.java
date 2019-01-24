package com.bw.movie.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.ListAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.HotMovie;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.presenter.ReleasePresenter;
import com.bw.movie.presenter.SoonPresenter;
import com.bw.movie.utils.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class ListActivity extends AppCompatActivity implements CustomAdapt {

    private RecyclerView recyclerView;
    private RadioGroup group;
    private ListAdapter adapter;
    private RadioButton list_mbutton1,list_mbutton2,list_mbutton3;
    private TextView tv_sou;
    private EditText et_sou;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //绑定
        ButterKnife.bind(this);
        //初始化控件
        list_mbutton1 = findViewById(R.id.list_mbutton1);
        list_mbutton2 = findViewById(R.id.list_mbutton2);
        list_mbutton3 = findViewById(R.id.list_mbutton3);
        tv_sou = findViewById(R.id.tv_sou);
        et_sou = findViewById(R.id.et_sou);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        AllUser allUser = users.get(0);
        userId = allUser.getUserId();
        sessionId = allUser.getSessionId();

        initView();
        adapter = new ListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        onItemClick(adapter);
        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
        hotMoviePresenter.request(userId, sessionId, 1, 500);
        group.check(group.getChildAt(0).getId());
        list_mbutton1.setTextColor(Color.WHITE);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.list_mbutton1:
                        list_mbutton1.setTextColor(Color.WHITE);
                        list_mbutton2.setTextColor(Color.BLACK);
                        list_mbutton3.setTextColor(Color.BLACK);
                        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
                        hotMoviePresenter.request(userId, sessionId, 1, 500);
                        break;
                    case R.id.list_mbutton2:
                        list_mbutton1.setTextColor(Color.BLACK);
                        list_mbutton2.setTextColor(Color.WHITE);
                        list_mbutton3.setTextColor(Color.BLACK);
                        ReleasePresenter releasePresenter = new ReleasePresenter(new ReleaseData());
                        releasePresenter.request(userId, sessionId, 1, 500);
                        break;
                    case R.id.list_mbutton3:
                        list_mbutton1.setTextColor(Color.BLACK);
                        list_mbutton2.setTextColor(Color.BLACK);
                        list_mbutton3.setTextColor(Color.WHITE);
                        SoonPresenter soonPresenter = new SoonPresenter(new SoonData());
                        soonPresenter.request(userId, sessionId, 1, 500);
                        break;
                }
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.list_recycer);
        group = findViewById(R.id.list_mRadio_cinema);
    }
    //点击实现搜索
    @OnClick(R.id.sou)
    public void sou(){
        tv_sou.setVisibility(View.VISIBLE);
        et_sou.setVisibility(View.VISIBLE);
    }
    //点击搜索隐藏
    @OnClick(R.id.tv_sou)
    public void et_sou(){
        et_sou.setVisibility(View.GONE);
        tv_sou.setVisibility(View.GONE);
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    //热门电影
    private class HorMovieData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            adapter.clearList();
            adapter.addList(data.getResult());
            adapter.notifyDataSetChanged();
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //正在热销
    private class ReleaseData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            adapter.clearList();
            adapter.addList(data.getResult());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    //即将上映
    private class SoonData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            adapter.clearList();
            adapter.addList(data.getResult());
            adapter.notifyDataSetChanged();
        }
        @Override

        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
    private void onItemClick(ListAdapter adapter){
        adapter.setOnclickItem(new ListAdapter.OnclickItem() {
            @Override
            public void OnclickItem(View view) {
                Toast.makeText(ListActivity.this, recyclerView.getChildAdapterPosition(view)+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
