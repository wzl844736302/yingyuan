package com.bw.movie.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.ScheduleList;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<ScheduleList> list = new ArrayList<>();
    private LayoutInflater inflater;
    public ScheduleAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.cinema_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(list.get(i).getScreeningHall());
        myHolder.textView2.setText(list.get(i).getBeginTime());
        myHolder.textView3.setText(list.get(i).getEndTime());
        myHolder.textView4.setText(list.get(i).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ScheduleList> result) {
        if (result!=null){
            list.clear();
            list.addAll(result);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ting);
            textView2 = itemView.findViewById(R.id.begintime);
            textView3 = itemView.findViewById(R.id.endtime);
            textView4 = itemView.findViewById(R.id.price);
        }
    }
}
