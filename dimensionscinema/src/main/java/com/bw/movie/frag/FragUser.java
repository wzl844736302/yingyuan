package com.bw.movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.view.LoginActivity;
import com.bw.movie.view.MyInForMation;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

public class FragUser extends Fragment implements CustomAdapt {

    private Unbinder bind;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    @BindView(R.id.msim_user)
    SimpleDraweeView msim_user;
    @BindView(R.id.text_login_regist)
    TextView mtext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user,container,false);
        //绑定
        bind = ButterKnife.bind(this, view);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
            String headPic = allUser.getHeadPic();
            msim_user.setImageURI(headPic);
            String nickName = allUser.getNickName();
            mtext.setText(nickName);
        }
        return view;
    }
    //点击登录
    @OnClick(R.id.text_login_regist)
    public void login(){
        //跳转
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
    //点击跳转我的信息
    @OnClick(R.id.wodexinxi)
    public void xinxi(){
        Intent intent = new Intent(getActivity(), MyInForMation.class);
        startActivity(intent);
    }
    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
            String headPic = allUser.getHeadPic();
            msim_user.setImageURI(headPic);
            String nickName = allUser.getNickName();
            mtext.setText(nickName);
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
