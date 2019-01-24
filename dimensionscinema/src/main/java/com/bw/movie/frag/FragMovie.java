package com.bw.movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.HotAdapter;
import com.bw.movie.adapter.HotMovieAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.HotMovie;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.presenter.ReleasePresenter;
import com.bw.movie.presenter.SoonPresenter;
import com.bw.movie.view.ListActivity;
import com.bw.movie.view.RecyclerCoverFlow;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragMovie extends Fragment implements HotMovieAdapter.onItemClick,View.OnClickListener {

    private RecyclerCoverFlow mList;
    private HotMovieAdapter hotMovieAdapter;
    private List<HotMovie> list = new ArrayList<>();
    private RecyclerView hotrcycler,beingrcycler,soonrecycler;
    private HotAdapter hotAdapter;
    private HotAdapter hotAdapter1;
    private HotAdapter hotAdapter2;
    private Unbinder bind;
    private TextView tv_sou;
    private EditText et_sou;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_movie, container, false);
        //绑定
        bind = ButterKnife.bind(this, view);
        tv_sou = view.findViewById(R.id.tv_sou);
        et_sou = view.findViewById(R.id.et_sou);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        AllUser allUser = users.get(0);
        userId = allUser.getUserId();
        sessionId = allUser.getSessionId();
        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
        hotMoviePresenter.request(userId, sessionId, 1, 500);

        ReleasePresenter releasePresenter = new ReleasePresenter(new ReleaseData());
        releasePresenter.request(userId, sessionId, 1, 500);


        SoonPresenter soonPresenter = new SoonPresenter(new SoonData());
        soonPresenter.request(userId, sessionId, 1, 500);

        initVist(view);


        //热门影院
        hotAdapter = new HotAdapter(getActivity());
        hotrcycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        hotrcycler.setAdapter(hotAdapter);

        //正在热映
        hotAdapter2 = new HotAdapter(getActivity());
        beingrcycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        beingrcycler.setAdapter(hotAdapter2);


        //即将上映
        hotAdapter1 = new HotAdapter(getActivity());
        soonrecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        soonrecycler.setAdapter(hotAdapter1);
        onItemClick(hotAdapter);
        onItemClick(hotAdapter1);
        onItemClick(hotAdapter2);

        return view;
    }

    private void initVist(View view) {
        mList = view.findViewById(R.id.list);
        hotMovieAdapter = new HotMovieAdapter(list, getActivity(), this);
        mList.setAdapter(hotMovieAdapter);
        hotrcycler = view.findViewById(R.id.home_recycler_hotmovie);
        beingrcycler = view.findViewById(R.id.home_recycler_being);
        soonrecycler = view.findViewById(R.id.home_recycler_soon);
        view.findViewById(R.id.movie_image).setOnClickListener(this);
        view.findViewById(R.id.movie_image1).setOnClickListener(this);
        view.findViewById(R.id.movie_image2).setOnClickListener(this);
    }

    @Override
    public void clickItem(int pos) {
        mList.smoothScrollToPosition(pos);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.movie_image:
                goListActivity();
                break;
            case R.id.movie_image1:
                goListActivity();
                break;
            case R.id.movie_image2:
                goListActivity();
                break;
        }
    }
    private void goListActivity(){
        startActivity(new Intent(getActivity(),ListActivity.class));
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
    //热门电影
    private class HorMovieData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            list.addAll(data.getResult());
            hotMovieAdapter.notifyDataSetChanged();
            hotAdapter.addList(data.getResult());
            hotAdapter.notifyDataSetChanged();
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //正在热销
    private class ReleaseData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {

            hotAdapter1.addList(data.getResult());
            hotAdapter1.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //即将上映
    private class SoonData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            hotAdapter2.addList(data.getResult());
            hotAdapter2.notifyDataSetChanged();
        }
        @Override

        public void fail(ApiException e) {
        }
    }
    private void onItemClick(HotAdapter adapter){
        adapter.setOnclickItem(new HotAdapter.OnclickItem() {
            @Override
            public void OnclickItem(View view) {
                Toast.makeText(getActivity(), soonrecycler.getChildAdapterPosition(view)+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}
