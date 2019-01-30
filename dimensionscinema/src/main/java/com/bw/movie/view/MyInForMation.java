package com.bw.movie.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.QureyUser;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.UpUser;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.frag.FragUser;
import com.bw.movie.presenter.QureyUserPresenter;
import com.bw.movie.presenter.UpUserPresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyInForMation extends WDActivity implements View.OnClickListener {
    @BindView(R.id.msim_my)
    SimpleDraweeView msim_my;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_birthdate)
    TextView tv_birthdate;
    @BindView(R.id.tv_telephone)
    TextView tv_telphone;
    @BindView(R.id.tv_email)
    TextView tv_email;
    private QureyUserPresenter qureyUserPresenter;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private SharedPreferences sp;
    private boolean xian;
    private UpUserPresenter upUserPresenter;
    private String trim;
    private int index = 0;// 记录单选对话框的下标
    private String newsex;
    private String nickName;
    private int sex;
    private QureyUser result;

    @Override
    protected void initView() {
        //绑定
        ButterKnife.bind(this);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //设置数据
        qureyUserPresenter = new QureyUserPresenter(new QureyUserCall());
        qureyUserPresenter.request(userId, sessionId);
        //修改用户信息
        myupdate();
    }

    private void myupdate() {
        findViewById(R.id.tv_username).setOnClickListener(this);
        findViewById(R.id.tv_sex).setOnClickListener(this);
        findViewById(R.id.tv_email).setOnClickListener(this);
        upUserPresenter = new UpUserPresenter(new UpUserCall());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_in_for_mation;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_username:
                final EditText editText = new EditText(this);
                editText.setText(result.getNickName());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("修改用户名称");
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = editText.getText().toString().trim();
                        users.get(0).setNickName(s);
                        tv_username.setText(s);
                        upUserPresenter.request(userId,sessionId,s,result.getSex(),result.getEmail());
                        Toast.makeText(MyInForMation.this, ""+userId+"  "+sessionId+"  "+s+"  "+result.getSex()+"  "+result.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
                break;
            case R.id.tv_sex:
                final int sex1 = result.getSex();
                int sexid = 0;
                if (sex1 == 1) {
                    sexid = 0;
                } else {
                    sexid = 1;
                }
                final String[] sex = {"男", "女"};
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setSingleChoiceItems(sex, sexid, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index = which;
                    }
                });
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newsex = sex[index];
                        tv_sex.setText(newsex);
                        if (newsex.equals("男")) {
                            upUserPresenter = new UpUserPresenter(new UpUserCall());
                            upUserPresenter.request(userId,sessionId,nickName,1,result.getEmail());
                        } else {
                            upUserPresenter = new UpUserPresenter(new UpUserCall());
                            upUserPresenter.request(userId, sessionId, nickName,2, result.getEmail());

                        }
                    }
                });
                // 显示对话框
                builder2.show();
                break;
            case R.id.tv_email:
                break;
        }
    }

    //实现查询用户信息
    class QureyUserCall implements DataCall<Result<QureyUser>> {
        @Override
        public void success(Result<QureyUser> data) {
            if (data.getStatus().equals("0000")) {
                result = data.getResult();
                String headPic = result.getHeadPic();
                msim_my.setImageURI(headPic);
                nickName = result.getNickName();
                tv_username.setText(nickName);
                sex = result.getSex();
                String s = sex == 1 ? "男" : "女";
                tv_sex.setText(s);
                long birthday = result.getBirthday();
                tv_birthdate.setText(birthday + "");
                String phone = result.getPhone();
                tv_telphone.setText(phone);
                String email = result.getEmail();
                tv_email.setText(email);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //实现修改资料的接口
    class UpUserCall implements DataCall<Result<UpUser>>{

        @Override
        public void success(Result<UpUser> data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(MyInForMation.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(MyInForMation.this, "222", Toast.LENGTH_SHORT).show();
        }
    }
    //点击返回
    @OnClick(R.id.mreturn)
    public void mreturn() {
        finish();
    }

}
