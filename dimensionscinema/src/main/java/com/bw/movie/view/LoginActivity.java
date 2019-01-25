package com.bw.movie.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.frag.FragUser;
import com.bw.movie.presenter.LoginPresenter;
import com.bw.movie.utils.util.EncryptUtil;
import com.bw.movie.utils.util.UIUtils;

import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private Button logBtn;
    private EditText numEd,pwdEd;
    private boolean iseye = true;
    private CheckBox eyeCb;
    private SharedPreferences sp;
    private String pwdStr;
    private String numStr;
    private CheckBox save_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        sp = getSharedPreferences("login",Context.MODE_PRIVATE);
        if (sp.getBoolean("bool",false)){
            String phone = sp.getString("phone", "");
            String pwd = sp.getString("pwd", "");
            numEd.setText(phone);
            pwdEd.setText(pwd);
            save_pwd.setChecked(true);
        }
    }

    private void initView() {
        logBtn = findViewById(R.id.login_btn);
        logBtn.setOnClickListener(this);
        numEd = findViewById(R.id.login_number_ed);
        pwdEd = findViewById(R.id.login_password_ed);
        eyeCb = findViewById(R.id.login_eye_cb);
        save_pwd = findViewById(R.id.save_pwd);
        eyeCb.setOnClickListener(this);
        findViewById(R.id.btn_regirect_tiao).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                pwdStr = pwdEd.getText().toString().trim();
                numStr = numEd.getText().toString().trim();
                //AES对称加密
                String encryptStr = EncryptUtil.encrypt(pwdStr);
               // Toast.makeText(this, pwdStr+encryptStr+"", Toast.LENGTH_SHORT).show();
                LoginPresenter loginPresenter = new LoginPresenter(new LoginData());
                loginPresenter.request(numStr,encryptStr);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("phone",numStr);
                edit.putString("pwd",pwdStr);
                edit.putBoolean("xian",true);
                edit.putBoolean("bool",save_pwd.isChecked());
                edit.commit();
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
            //登陆成功回调
            if(data.getStatus().equals("0000")){
                //添加数据库
                User result = data.getResult();
                AllUser allUser = new AllUser(1,result.getSessionId(),result.getUserId(),result.getUserInfo().getNickName(),result.getUserInfo().getPhone(),result.getUserInfo().getBirthday(),result.getUserInfo().getSex(),result.getUserInfo().getLastLoginTime(),result.getUserInfo().getHeadPic());
                AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
                allUserDao.insertOrReplace(allUser);
                finish();
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
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
