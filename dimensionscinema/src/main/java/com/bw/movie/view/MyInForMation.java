package com.bw.movie.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.QureyUser;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.QureyUserPresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyInForMation extends WDActivity {
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
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_in_for_mation;
    }

    //实现查询用户信息
    class QureyUserCall implements DataCall<Result<QureyUser>> {
        @Override
        public void success(Result<QureyUser> data) {
            if (data.getStatus().equals("0000")) {
                QureyUser result = data.getResult();
                String headPic = result.getHeadPic();
                msim_my.setImageURI(headPic);
                String nickName = result.getNickName();
                tv_username.setText(nickName);
                int sex = result.getSex();
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

    //点击返回
    @OnClick(R.id.mreturn)
    public void mreturn() {
        finish();
    }

}
