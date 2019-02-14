package com.bw.movie.view;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.RegsterPresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.bw.movie.utils.util.EncryptUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegsterActivity extends WDActivity implements View.OnClickListener {

    private EditText emailEd, numberEd, pwdEd, pwd2Ed, nameEd;
    private TextView datate;

    @Override
    protected void initView() {
        emailEd = findViewById(R.id.edit_register_email);
        numberEd = findViewById(R.id.edit_register_number);
        pwdEd = findViewById(R.id.edit_register_pwd);
        /* pwd2Ed = findViewById(R.id.edit_register_pwd2);*/
        nameEd = findViewById(R.id.edit_regiter_name);
        datate = findViewById(R.id.edit_register_date);
        findViewById(R.id.btn_register).setOnClickListener(this);
        datate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegsterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        datate.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, 2000, 1, 2).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regster;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String emailStr = emailEd.getText().toString().trim();
                boolean email = isEmail(emailStr);
                if (email) {
                    String numberStr = numberEd.getText().toString().trim();
                    boolean b = compileExChar(numberStr);
                    if(b){
                    String pwdStr = pwdEd.getText().toString().trim();
                    /*String pwd2Str = pwd2Ed.getText().toString().trim();*/
                    String nameStr = nameEd.getText().toString().trim();
                        boolean b1 = compileExChar(nameStr);
                        if (b1){
                            String pwdStr1 = EncryptUtil.encrypt(pwdStr);
                            RegsterPresenter regsterPresenter = new RegsterPresenter(new RegData());
                            regsterPresenter.request(nameStr, numberStr, pwdStr1, pwdStr1, 1, datate.getText(), "移动设备识别码", "设备类型", "屏幕尺寸", "手机系统", emailStr);
                        }else {
                            Toast.makeText(this, "禁止输入非法字符", Toast.LENGTH_SHORT).show();
                        }
                        }else {
                        Toast.makeText(this, "禁止输入非法字符", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "您的邮箱格式有误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class RegData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(RegsterActivity.this, data.getMessage() + "", Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
        }
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

    private boolean compileExChar(String str) {

        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        return m.find();
     /*   if () {
            Toast.makeText(RegsterActivity.this, "不允许输入特殊符号！", Toast.LENGTH_LONG).show();
            return;
        }*/

    }

}
