package com.bw.movie.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.RecommendAdapter;
import com.bw.movie.bean.Recommend;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.RecommendPresenter;
import java.util.List;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

public class FragCinema extends Fragment implements CustomAdapt {

    private RadioGroup mradio_cinema;
    private Button mbutton1;
    private Button mbutton2;
    private RecyclerView mrecycler_cinema;
    private RecommendPresenter recommendPresenter;
    private RecommendAdapter recommendAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cinema,container,false);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //初始化控件
        mradio_cinema = view.findViewById(R.id.mRadio_cinema);
        mbutton1 = view.findViewById(R.id.mbutton1);
        mbutton2 = view.findViewById(R.id.mbutton2);
        mrecycler_cinema = view.findViewById(R.id.mrecycler_cinema);
        //设置适配器
        recommendAdapter = new RecommendAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecycler_cinema.setLayoutManager(linearLayoutManager);
        mrecycler_cinema.setAdapter(recommendAdapter);
        //设置数据
        recommendPresenter = new RecommendPresenter(new RecommendCall());
        recommendPresenter.request(1770,"15482453997081770",1,10);
        //默认选中第一个
        mradio_cinema.check(mradio_cinema.getChildAt(0).getId());
        mbutton1.setTextColor(Color.WHITE);
        //点击选中切换
        mradio_cinema.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mbutton1:
                        mbutton1.setTextColor(Color.WHITE);
                        mbutton2.setTextColor(Color.BLACK);
                        break;
                    case R.id.mbutton2:
                        mbutton1.setTextColor(Color.BLACK);
                        mbutton2.setTextColor(Color.WHITE);
                        break;
                }
            }
        });
        return view;
    }
    //实现推荐影院接口
    class RecommendCall implements DataCall<Result<List<Recommend>>>{
        @Override
        public void success(Result<List<Recommend>> data) {
            if (data.getStatus().equals("0000")){
                List<Recommend> result = data.getResult();
                recommendAdapter.addAll(result);
                recommendAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }
    @Override
    public float getSizeInDp() {
        return 720;
    }
}
