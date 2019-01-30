package com.bw.movie.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MessageList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<MessageList> list = new ArrayList<>();
    private LayoutInflater inflater;
    public MessageAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.news_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.textView.setText(list.get(i).getTitle());
        myHolder.textView2.setText(list.get(i).getContent());
        long pushTime = list.get(i).getPushTime();
        Date date = new Date(pushTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s = format.format(date);
        myHolder.textView3.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<MessageList> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tit);
            textView2 = itemView.findViewById(R.id.shijian);
            textView3 = itemView.findViewById(R.id.count);
        }
    }
}
