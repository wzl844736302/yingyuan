package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.MovieDetailList;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

public class Btn2Adapter extends RecyclerView.Adapter {


    private List<MovieDetailList> mList = new ArrayList<>();
    private Context context;


    private OnclickItem onclickItem;
    private  LayoutInflater from;

    public void setOnclickItem(OnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }

    public Btn2Adapter(Context context) {
        this.context = context;
        from = LayoutInflater.from(context);
    }
    public void addList(List<MovieDetailList> data){
        mList.addAll(data);
    }
    public List<MovieDetailList> getList(){
        return mList;
    }
    public void clearList(){
        mList.clear();
        notifyDataSetChanged();
    }
    public void delList(int i){
        mList.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = from.inflate(R.layout.view_item,viewGroup,false);
        MyHodler myHodler = new MyHodler(view);
        return myHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder  viewHolder,final int  i) {
        MyHodler myHodler = new MyHodler(viewHolder.itemView);

        myHodler.jzVideoPlayerStandard.setUp(mList.get(i).getVideoUrl()
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        Glide.with(context).load(mList.get(i).getImageUrl()).into( myHodler.jzVideoPlayerStandard.thumbImageView);

        }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHodler extends RecyclerView.ViewHolder {

        private  JZVideoPlayerStandard jzVideoPlayerStandard;

        public MyHodler(View itemView) {
            super(itemView);
            jzVideoPlayerStandard = itemView.findViewById(R.id.view_videoplayer);
        }
    }
   public  interface OnclickItem{
        void OnclickItem(View view);
    }
}
