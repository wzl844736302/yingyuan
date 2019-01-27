package com.bw.movie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Recommend;
import com.bw.movie.view.DetailCinemaActivity;

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
    private CallLove callLove;
    public interface CallLove{
        void love(int id,int ischeck);
    }

    public void setCallLove(CallLove callLove) {
        this.callLove = callLove;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recommend_item,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        MyHolder myHolder = (MyHolder) holder;
        final Recommend recommend = list.get(i);
        String logo = list.get(i).getLogo();
        myHolder.imageView.setImageURI(Uri.parse(logo));
        myHolder.textView.setText(list.get(i).getName());
        myHolder.textView2.setText(list.get(i).getAddress());
        myHolder.textView3.setText("0km");
        final int id = recommend.getId();
        //点击跳转
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(i).getId();
                Intent intent = new Intent(activity.getBaseContext(), DetailCinemaActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("tupian",list.get(i).getLogo());
                intent.putExtra("name",list.get(i).getName());
                intent.putExtra("weizhi",list.get(i).getAddress());
                activity.startActivity(intent);
            }
        });
        //是否关注
        myHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                recommend.setIscheck(checked?1:0);
                callLove.love(id,recommend.getIscheck());
            }
        });
        if (recommend.getIscheck()==0){
            myHolder.checkBox.setChecked(false);
        }else {
            myHolder.checkBox.setChecked(true);
        }
        myHolder.checkBox.setChecked(recommend.getFollowCinema()==1?true:false);
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

    public void clear() {
        list.clear();
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
