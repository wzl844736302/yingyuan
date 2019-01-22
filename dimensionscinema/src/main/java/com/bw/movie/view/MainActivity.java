package com.bw.movie.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bw.movie.R;
import me.jessyan.autosize.internal.CustomAdapt;

public class MainActivity extends AppCompatActivity implements CustomAdapt {

    int i = 3;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                i--;
                if(i<=0){
                   /* Intent intent = new Intent(MainActivity.this, JumpActivity.class);
                    startActivity(intent);
                    finish();*/
                    return;
                }
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessageDelayed(1,1000);
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
