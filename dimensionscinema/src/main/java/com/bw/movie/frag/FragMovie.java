package com.bw.movie.frag;

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
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.HotAdapter;
import com.bw.movie.adapter.HotMovieAdapter;
import com.bw.movie.bean.HotMovie;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.view.RecyclerCoverFlow;

import java.util.ArrayList;
import java.util.List;

public class FragMovie extends Fragment implements HotMovieAdapter.onItemClick {

    private RecyclerCoverFlow mList;
    private HotMovieAdapter hotMovieAdapter;
    private List<HotMovie> list = new ArrayList<>();
    private RecyclerView hotrcycler;
    private HotAdapter hotAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_movie, container, false);

        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
        hotMoviePresenter.request(1770, "15482453997081770", 1, 500);
        initVist(view);
        hotAdapter = new HotAdapter(getActivity());
        hotrcycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        hotrcycler.setAdapter(hotAdapter);
        return view;
    }

    private void initVist(View view) {
        mList = view.findViewById(R.id.list);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
        //  mList.setAlphaItem(true); //设置半透渐变
        hotMovieAdapter = new HotMovieAdapter(list, getActivity(), this);
        mList.setAdapter(hotMovieAdapter);
        hotrcycler = view.findViewById(R.id.home_recycler_hotmovie);


    }

    @Override
    public void clickItem(int pos) {
        mList.smoothScrollToPosition(pos);
    }

    private class HorMovieData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            Toast.makeText(getActivity(), data.getResult().size() + "", Toast.LENGTH_SHORT).show();
            list.addAll(data.getResult());
            hotMovieAdapter.notifyDataSetChanged();

            hotAdapter.addList(data.getResult());
            hotAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
