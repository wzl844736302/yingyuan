package com.bw.movie.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.HotAdapter;
import com.bw.movie.adapter.ListAdapter;
import com.bw.movie.bean.HotMovie;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.frag.FragMovie;
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.presenter.ReleasePresenter;
import com.bw.movie.presenter.SoonPresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class ListActivity extends AppCompatActivity implements CustomAdapt {

    private RecyclerView recyclerView;
    private RadioGroup group;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        adapter = new ListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        onItemClick(adapter);
        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
        hotMoviePresenter.request(1770, "15482453997081770", 1, 500);
        group.check(group.getChildAt(0).getId());
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.list_mbutton1:
                        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
                        hotMoviePresenter.request(1770, "15482453997081770", 1, 500);

                        break;
                    case R.id.list_mbutton2:
                        ReleasePresenter releasePresenter = new ReleasePresenter(new ReleaseData());
                        releasePresenter.request(1770, "15482453997081770", 1, 500);
                        break;
                    case R.id.list_mbutton3:
                        SoonPresenter soonPresenter = new SoonPresenter(new SoonData());
                        soonPresenter.request(1770, "15482453997081770", 1, 500);
                        break;
                }
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.list_recycer);
        group = findViewById(R.id.list_mRadio_cinema);
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

        }
    }
    private void onItemClick(final ListAdapter adapter){
        adapter.setOnclickItem(new ListAdapter.OnclickItem() {
            @Override
            public void OnclickItem(View view) {
               // Toast.makeText(ListActivity.this, recyclerView.getChildAdapterPosition(view)+"", Toast.LENGTH_SHORT).show();
                List<HotMovie> list = adapter.getList();
                Intent intent = new Intent(ListActivity.this,DetailsActivity.class);
                intent.putExtra("id",list.get(recyclerView.getChildAdapterPosition(view)).getId());
                startActivity(intent);
            }
        });
    }
}
