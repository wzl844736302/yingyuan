package com.bw.movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.view.LoginActivity;

import me.jessyan.autosize.internal.CustomAdapt;

public class Frag_four extends Fragment implements View.OnClickListener {

    private RelativeLayout mrelate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_four,container,false);
        //初始化控件
        mrelate = view.findViewById(R.id.mRelate);
        mrelate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //点击跳转
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
