package com.bw.movie.utils.jilei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.bw.movie.MyApp;
import com.bw.movie.dao.AllUserDao;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

public abstract class WDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);//绑定布局
        initView();
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
    /**
     * 设置layoutId
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * @param mClass 传送Activity的
     */
    public void intent(Class mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }
     /**
     * @param myClass 传送Activity的
     * @param bundle
     */
    public void intent(Class myClass, Bundle bundle) {
        Intent intent = new Intent(this, myClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("页面开始");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("页面结束");
        MobclickAgent.onPause(this);
    }
}
