package com.bw.movie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemasList;
import com.bw.movie.view.TicketActivity;
import com.bw.movie.view.TicketDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<CinemasList> list = new ArrayList<>();
    private LayoutInflater inflater;
    public TicketAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }
    private HuiDiao huiDiao;
    public interface HuiDiao{
        void huidiao(int pos,int id1);
    }
    public void setsHuiDiao(HuiDiao huiDiao) {
        this.huiDiao = huiDiao;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.buycinemas_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        MyHolder myHolder = (MyHolder) holder;
        String logo = list.get(i).getLogo();
        myHolder.draweeView.setImageURI(logo);
        myHolder.textView.setText(list.get(i).getName());
        myHolder.textView2.setText(list.get(i).getAddress());
        myHolder.textView3.setText("0km");
        //点击跳转
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               huiDiao.huidiao(i,list.get(i).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<CinemasList> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView;
        TextView textView;
        TextView textView2;
        TextView textView3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.iv1);
            textView = itemView.findViewById(R.id.name);
            textView2 = itemView.findViewById(R.id.address);
            textView3 = itemView.findViewById(R.id.juli);
        }
    }
}
