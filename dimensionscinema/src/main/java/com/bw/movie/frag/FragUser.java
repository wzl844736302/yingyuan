package com.bw.movie.frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.msimUser)
    SimpleDraweeView msim_user;
    @BindView(R.id.textLoginRegist)
    TextView mtext;
    private AllUserDao allUserDao;
    private SharedPreferences sp;
    private boolean xian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user,container,false);
        //绑定
        bind = ButterKnife.bind(this, view);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //查询数据库
        allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            String headPic = allUser.getHeadPic();
            msim_user.setImageURI(headPic);
            String nickName = allUser.getNickName();
            mtext.setText(nickName);
        }
        sp = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        xian = sp.getBoolean("xian", false);
        return view;
    }
    //点击登录
    @OnClick(R.id.textLoginRegist)
    public void login(){
        //跳转
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
    //点击跳转我的信息
    @OnClick(R.id.wodexinxi)
    public void xinxi(){
        if (!xian){
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), MyInForMation.class);
        startActivity(intent);
    }
    //点击退出登录
    @OnClick(R.id.tuichudenglu)
    public void tuichu(){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("xian", false);
        xian = false;
        edit.commit();
        //跳转
        Intent intent = new Intent(getActivity(), LoginActivity.class);
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
        xian = sp.getBoolean("xian", false);
        if (!xian){
            mtext.setText("登录/注册");
            return;
        }
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
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
