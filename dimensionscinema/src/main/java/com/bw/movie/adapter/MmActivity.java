package com.bw.movie.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.PassPresenter;
import com.bw.movie.utils.util.EncryptUtil;

import java.util.ArrayList;
import java.util.List;

public class MmActivity extends AppCompatActivity {
    private int userId;
    private String sessionId;
    private List<AllUser> users = new ArrayList<>();
    private EditText mm,mm1,mm2;
    private Button butY;
    private ImageView ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);
        initview();
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0){
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        butY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strmm = mm.getText().toString().trim();
                String strmm1 = mm1.getText().toString().trim();
                String strmm2 = mm2.getText().toString().trim();
                String encryptStr = EncryptUtil.encrypt(strmm);
                String encryptStr1 = EncryptUtil.encrypt(strmm1);
                String encryptStr2 = EncryptUtil.encrypt(strmm2);
                PassPresenter passPresenter = new PassPresenter(new PassData());
                passPresenter.request(userId,sessionId,encryptStr,encryptStr1,encryptStr2);
            }
        });
    }

    private void initview() {
        mm = findViewById(R.id.mm_m);
        mm1 = findViewById(R.id.mm_m1);
        mm2 = findViewById(R.id.mm_m2);
        mm2 = findViewById(R.id.mm_m2);
        butY=findViewById(R.id.mm_y);
        ret = findViewById(R.id.mm_return);
    }


    private class PassData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(MmActivity.this, data.getMessage()+"", Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")){
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
