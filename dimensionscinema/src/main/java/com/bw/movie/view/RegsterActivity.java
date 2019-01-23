package com.bw.movie.view;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.RegsterPresenter;
import com.bw.movie.utils.util.EncryptUtil;

import me.jessyan.autosize.internal.CustomAdapt;

public class RegsterActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private EditText emailEd,numberEd,pwdEd,pwd2Ed,nameEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regster);
        initView();
    }

    private void initView() {
        emailEd = findViewById(R.id.edit_register_email);
        numberEd = findViewById(R.id.edit_register_number);
        pwdEd = findViewById(R.id.edit_register_pwd);
       /* pwd2Ed = findViewById(R.id.edit_register_pwd2);*/
        nameEd = findViewById(R.id.edit_regiter_name);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                String emailStr = emailEd.getText().toString().trim();
                String numberStr = numberEd.getText().toString().trim();
                String pwdStr =   pwdEd.getText().toString().trim();
                /*String pwd2Str = pwd2Ed.getText().toString().trim();*/
                String nameStr = nameEd.getText().toString().trim();
                String pwdStr1 = EncryptUtil.encrypt(pwdStr);
                RegsterPresenter regsterPresenter = new RegsterPresenter(new RegData());
                regsterPresenter.request(nameStr,numberStr,pwdStr1,pwdStr1,1,"出生日期","移动设备识别码","设备类型","屏幕尺寸","手机系统",emailStr);
                break;
        }
    }
    private class RegData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(RegsterActivity.this, data.getMessage()+"", Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")){
               finish();
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
