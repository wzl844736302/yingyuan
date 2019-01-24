package com.bw.movie;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.bean.Recommend;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<Recommend> list = new ArrayList<>();
    private LayoutInflater inflater;
    public RecommendAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recommend_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(list.get(i).getName());
        myHolder.textView2.setText(list.get(i).getAddress());
        myHolder.textView3.setText(list.get(i).getFollowCinema()+""+"km");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<Recommend> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        CheckBox checkBox;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mtupian);
            textView = itemView.findViewById(R.id.mname);
            textView2 = itemView.findViewById(R.id.mdizhi);
            textView3 = itemView.findViewById(R.id.mqianmi);
            checkBox = itemView.findViewById(R.id.mguanzhu);
        }
    }
}
