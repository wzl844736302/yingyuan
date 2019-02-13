package com.bw.movie.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.bw.movie.R;
import com.bw.movie.utils.jilei.WDActivity;

public class MainActivity extends WDActivity {

    int i = 3;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                i--;
                if(i<=0){
                    Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {
     handler.sendEmptyMessageDelayed(1,1000);
    }
}