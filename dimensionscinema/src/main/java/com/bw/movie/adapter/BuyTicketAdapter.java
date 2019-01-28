package com.bw.movie.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.BuyTicketList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuyTicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<BuyTicketList> list = new ArrayList<>();
    private LayoutInflater inflater;
    public BuyTicketAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }
    private SingleBack singleBack;
    public interface SingleBack{
        void back(int pos,int uid,int count,String hao);
    }

    public void setSingleBack(SingleBack singleBack) {
        this.singleBack = singleBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            View view = inflater.inflate(R.layout.buyticket_item, viewGroup, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }else {
            View view = inflater.inflate(R.layout.buyticket_item1,viewGroup,false);
            MyHolder1 holder1 = new MyHolder1(view);
            return holder1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.textView.setText(list.get(i).getMovieName());
            myHolder.textView2.setText(list.get(i).getOrderId());
            myHolder.textView3.setText(list.get(i).getCinemaName());
            myHolder.textView4.setText(list.get(i).getScreeningHall());
            long createTime = list.get(i).getCreateTime();
            Date date = new Date(createTime);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String s = format.format(date);
            String beginTime = list.get(i).getBeginTime();
            String endTime = list.get(i).getEndTime();
            myHolder.textView5.setText(s + "   " + beginTime + "--" + endTime);
            myHolder.textView6.setText(list.get(i).getAmount() + "张");
            myHolder.textView7.setText(list.get(i).getPrice() + "元");
            //点击接口回调
            myHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleBack.back(i, list.get(i).getId(), list.get(i).getAmount(), list.get(i).getOrderId());
                }
            });
        }else {
            MyHolder1 myHolder = (MyHolder1) holder;
            myHolder.textView.setText(list.get(i).getMovieName());
            myHolder.textView2.setText(list.get(i).getOrderId());
            myHolder.textView3.setText(list.get(i).getCinemaName());
            myHolder.textView4.setText(list.get(i).getScreeningHall());
            long createTime = list.get(i).getCreateTime();
            Date date = new Date(createTime);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String s = format.format(date);
            String beginTime = list.get(i).getBeginTime();
            String endTime = list.get(i).getEndTime();
            myHolder.textView5.setText(s + "   " + beginTime + "--" + endTime);
            myHolder.textView6.setText(list.get(i).getAmount() + "张");
            myHolder.textView7.setText(list.get(i).getPrice() + "元");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getStatus()==1){
            return 1;
        }else {
            return 2;
        }
    }

    public void addAll(List<BuyTicketList> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public void clear() {
        list.clear();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
        Button button;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.moviename);
            textView2 = itemView.findViewById(R.id.ordernumber);
            textView3 = itemView.findViewById(R.id.cinemaname);
            textView4 = itemView.findViewById(R.id.moviehall);
            textView5 = itemView.findViewById(R.id.rccordtime);
            textView6 = itemView.findViewById(R.id.rccordnum);
            textView7 = itemView.findViewById(R.id.rccordmoney);
            button = itemView.findViewById(R.id.moviebuy);
        }
    }
    class MyHolder1 extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
        public MyHolder1(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.moviename);
            textView2 = itemView.findViewById(R.id.ordernumber);
            textView3 = itemView.findViewById(R.id.cinemaname);
            textView4 = itemView.findViewById(R.id.moviehall);
            textView5 = itemView.findViewById(R.id.rccordtime);
            textView6 = itemView.findViewById(R.id.rccordnum);
            textView7 = itemView.findViewById(R.id.rccordmoney);
        }
    }
}
