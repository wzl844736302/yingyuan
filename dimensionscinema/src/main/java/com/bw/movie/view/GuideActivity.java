package com.bw.movie.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.bw.movie.R;
import com.bw.movie.adapter.GuideAdapter;
import com.bw.movie.frag.Frag_four;
import com.bw.movie.frag.Frag_one;
import com.bw.movie.frag.Frag_three;
import com.bw.movie.frag.Frag_two;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

public class GuideActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.mView)
    ViewPager mview;
    private List<Fragment> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //绑定
        ButterKnife.bind(this);
        //添加到list里面
        list.add(new Frag_one());
        list.add(new Frag_two());
        list.add(new Frag_three());
        list.add(new Frag_four());
        //设置适配器
        GuideAdapter adapter = new GuideAdapter(getSupportFragmentManager(),list);
        mview.setAdapter(adapter);
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
