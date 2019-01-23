package com.bw.movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.view.MyInForMation;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

public class FragUser extends Fragment implements CustomAdapt {

    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user,container,false);
        //绑定
        bind = ButterKnife.bind(this, view);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        return view;
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
    public boolean isBaseOnWidth() {
        return false;
    }
    @Override
    public float getSizeInDp() {
        return 720;
    }
}
