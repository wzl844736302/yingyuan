package com.bw.movie.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    @BindView(R.id.mRadio)
    RadioGroup mradio;
    private List<Fragment> list = new ArrayList<>();
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //绑定
        ButterKnife.bind(this);
        sp = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        //添加到list里面
        list.add(new Frag_one());
        list.add(new Frag_two());
        list.add(new Frag_three());
        list.add(new Frag_four());
        //设置适配器
        GuideAdapter adapter = new GuideAdapter(getSupportFragmentManager(), list);
        mview.setAdapter(adapter);
        //默认选中第一个
        mradio.check(mradio.getChildAt(0).getId());
        //联动
        mview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mradio.check(mradio.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //判断
        if (sp.getBoolean("bool", false)) {
            Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        //启动页判断是否第一次启动
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("bool", true);
        edit.commit();
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
