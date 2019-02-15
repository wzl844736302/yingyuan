package com.bw.movie.view;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.frag.FragCinema;
import com.bw.movie.frag.FragMovie;
import com.bw.movie.frag.FragUser;
import com.bw.movie.utils.jilei.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class HomeActivity extends WDActivity {
    @BindView(R.id.mFrame_home)
    FrameLayout mframe_home;
    private RadioButton[] buttons;
    private Drawable[] drawables;
    private FragMovie fragMovie;
    private FragCinema fragCinema;
    private FragUser fragUser;
    private FragmentTransaction transaction;
    private RadioGroup mradio_home;

    @Override
    protected void initView() {
        //定义RadioButton数组用来装RadioButton，改变drawableTop大小
        buttons = new RadioButton[3];
        mradio_home = findViewById(R.id.mRadio_home);
        //将RadioButton装进数组中
        buttons[0] = findViewById(R.id.movie);
        buttons[1] = findViewById(R.id.cinema);
        buttons[2] = findViewById(R.id.user);
        //for循环对每一个RadioButton图片进行缩放
        for (int i = 0; i < buttons.length; i++) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            drawables = buttons[i].getCompoundDrawables();
            //获取drawables，2/5表示图片要缩小的比例
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 2 / 2, drawables[1].getMinimumHeight() * 2 / 2);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给每一个RadioButton设置图片大小
            buttons[i].setCompoundDrawables(null, drawables[1], null, null);
        }

        transaction = getSupportFragmentManager().beginTransaction();
        fragMovie = new FragMovie();
        fragCinema = new FragCinema();
        fragUser = new FragUser();
        transaction.add(R.id.mFrame_home,fragMovie);
        transaction.add(R.id.mFrame_home,fragCinema);
        transaction.add(R.id.mFrame_home,fragUser);
        transaction.show(fragMovie);
        transaction.hide(fragCinema);
        transaction.hide(fragUser);
        transaction.commit();
        //默认选中第一个
        mradio_home.check(mradio_home.getChildAt(0).getId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @OnClick({R.id.movie,R.id.cinema,R.id.user})
    public void onView(View view){
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.movie:
                transaction1.show(fragMovie);
                transaction1.hide(fragCinema);
                transaction1.hide(fragUser);
                break;
            case R.id.cinema:
                transaction1.show(fragCinema);
                transaction1.hide(fragMovie);
                transaction1.hide(fragUser);
                break;
            case R.id.user:
                transaction1.show(fragUser);
                transaction1.hide(fragCinema);
                transaction1.hide(fragMovie);
                break;

        }
        transaction1.commit();
    }
    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
