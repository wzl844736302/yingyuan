package com.bw.movie.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieList;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FocusOnAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Activity activity;
    private List<MovieList> list = new ArrayList<>();
    private LayoutInflater inflater;
    public FocusOnAdapter1(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.movie_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.draweeView.setImageURI(list.get(i).getImageUrl());
        myHolder.textView.setText(list.get(i).getName());
        myHolder.textView2.setText(list.get(i).getSummary());
        long releaseTime = list.get(i).getReleaseTime();
        Date date = new Date(releaseTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s = format.format(date);
        myHolder.textView3.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addAll(List<MovieList> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public void clear() {
        list.clear();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.movie_img);
            textView = itemView.findViewById(R.id.movie_name);
            textView2 = itemView.findViewById(R.id.movie_title);
            textView3 = itemView.findViewById(R.id.movie_time);
        }
    }
}
