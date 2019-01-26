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

import java.util.ArrayList;
import java.util.List;

public class DetailCinemaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<MovieList> list = new ArrayList<>();
    private LayoutInflater inflater;
    public DetailCinemaAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.detail_cinema_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        String imageUrl = list.get(i).getImageUrl();
        myHolder.draweeView.setImageURI(imageUrl);
        myHolder.textView.setText(list.get(i).getName());
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

    class MyHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView;
        TextView textView;
        TextView textView2;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.simCinema);
            textView = itemView.findViewById(R.id.tvName);
        }
    }
}
