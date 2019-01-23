package com.bw.movie.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.LoginPresenter;
import com.bw.movie.utils.util.EncryptUtil;

import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private Button logBtn;
    private EditText numEd,pwdEd;
    private boolean iseye = true;
    private CheckBox eyeCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        logBtn = findViewById(R.id.login_btn);
        logBtn.setOnClickListener(this);
        numEd = findViewById(R.id.login_number_ed);
        pwdEd = findViewById(R.id.login_password_ed);
        eyeCb = findViewById(R.id.login_eye_cb);
        eyeCb.setOnClickListener(this);
        findViewById(R.id.btn_regirect_tiao).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                String pwdStr = pwdEd.getText().toString().trim();
                String numStr = numEd.getText().toString().trim();
                //AES对称加密
                String encryptStr = EncryptUtil.encrypt(pwdStr);
               // Toast.makeText(this, pwdStr+encryptStr+"", Toast.LENGTH_SHORT).show();
                LoginPresenter loginPresenter = new LoginPresenter(new LoginData());
                loginPresenter.request(numStr,encryptStr);
                break;
            case R.id.login_eye_cb:
                if (iseye) {
                    //设置EditText文本为可见的
                    pwdEd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    pwdEd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                iseye = !iseye;
                break;
            case R.id.btn_regirect_tiao:
                startActivity(new Intent(LoginActivity.this,RegsterActivity.class));
                break;
        }
    }

    /**
     * 登陆返回的数据
     */
    private class LoginData implements DataCall<Result<User>> {
        @Override
        public void success(Result<User> data) {
            Toast.makeText(LoginActivity.this, data.getMessage()+"", Toast.LENGTH_SHORT).show();

            //登陆成功回调

            if(data.getStatus().equals("0000")){
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
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
