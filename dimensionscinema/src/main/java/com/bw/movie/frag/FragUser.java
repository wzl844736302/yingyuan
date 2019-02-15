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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.dao.DaoMaster;
import com.bw.movie.dao.DaoSession;
import com.bw.movie.presenter.SignInPresenter;
import com.bw.movie.view.BuyTicketActivity;
import com.bw.movie.view.FeedBackActivity;
import com.bw.movie.view.FocusOnActivity;
import com.bw.movie.view.LoginActivity;
import com.bw.movie.view.MessageActivity;
import com.bw.movie.view.MyInForMation;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;

public class FragUser extends Fragment {

    private Unbinder bind;
    private List<AllUser> users = new ArrayList<>();
    @BindView(R.id.msimUser)
    SimpleDraweeView msim_user;
    @BindView(R.id.textLoginRegist)
    TextView mtext;
    @BindView(R.id.mbt)
    Button mbt;
    private AllUserDao allUserDao;
    private SharedPreferences sp;
    private boolean xian;
    private String nickName;
    private SignInPresenter signInPresenter;
    private int userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user, container, false);
        //绑定
        bind = ButterKnife.bind(this, view);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //查询数据库
        allUserDao = MyApp.daoSession.getAllUserDao();
        List<AllUser> allUsers = allUserDao.loadAll();
        users.addAll(allUsers);
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            String headPic = allUser.getHeadPic();
            msim_user.setImageURI(headPic);
            String nickName = allUser.getNickName();
            mtext.setText(nickName);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        xian = sp.getBoolean("xian", false);
        //点击签到
        mbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInPresenter = new SignInPresenter(new SignInCall());
                signInPresenter.request(userId, sessionId);
            }
        });
        return view;
    }

    //实现签到接口
    class SignInCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "今天已签到", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //点击登录
    @OnClick(R.id.textLoginRegist)
    public void login() {
        //跳转
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    //点击跳转我的信息
    @OnClick(R.id.btn_msg_mine)
    public void xinxi() {
        if (!xian) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), MyInForMation.class);
        startActivity(intent);
    }

    //点击跳转我的关注
    @OnClick(R.id.btn_like_mine)
    public void guanzhu() {
        if (!xian) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        //跳转
        Intent intent = new Intent(getActivity(), FocusOnActivity.class);
        getActivity().startActivity(intent);
    }

    //点击跳转购票记录
    @OnClick(R.id.btn_rccord_mine)
    public void jilu() {
        if (!xian) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        //跳转
        Intent intent = new Intent(getActivity(), BuyTicketActivity.class);
        getActivity().startActivity(intent);
    }

    //点击跳转意见反馈
    @OnClick(R.id.btn_feedback_mine)
    public void fankui() {
        if (!xian) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        //跳转
        Intent intent = new Intent(getActivity(), FeedBackActivity.class);
        getActivity().startActivity(intent);
    }

    //点击最新版本
    @OnClick(R.id.btn_version_mine)
    public void banben() {
        Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_SHORT).show();
    }

    //点击退出登录
    @OnClick(R.id.btn_tuichu_mine)
    public void tuichu() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("xian", false);
        xian = false;
        edit.commit();
        mtext.setText("登录/注册");
        msim_user.setImageURI("");
        mbt.setVisibility(View.GONE);
    }

    //点击跳转系统消息
    @OnClick(R.id.ming_remind)
    public void ming() {
        if (!xian) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        //跳转
        Intent intent = new Intent(getActivity(), MessageActivity.class);
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
        if (!xian) {
            mtext.setText("登录/注册");
            return;
        }
        mbt.setVisibility(View.VISIBLE);
        //点击签到
        mbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInPresenter = new SignInPresenter(new SignInCall());
                signInPresenter.request(userId, sessionId);
            }
        });
        //查询数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), AllUserDao.TABLENAME);
        AllUserDao allUserDao = daoSession.getAllUserDao();
        users.clear();
        users.addAll(allUserDao.loadAll());
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            String headPic = allUser.getHeadPic();
            msim_user.setImageURI(headPic);
            nickName = allUser.getNickName();
            mtext.setText(nickName);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
    }
}