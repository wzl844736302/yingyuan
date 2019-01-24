package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.HotMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class HotMovieAdapter extends RecyclerView.Adapter<HotMovieAdapter.ViewHolder> {
 private List<HotMovie> list;
    private Context mContext;
    private onItemClick clickCb;

    public HotMovieAdapter(List<HotMovie> list, Context mContext, onItemClick clickCb) {
        this.list = list;
        this.mContext = mContext;
        this.clickCb = clickCb;
    }

    public HotMovieAdapter(Context c) {
        mContext = c;
    }


    public HotMovieAdapter(Context c, onItemClick cb) {
        mContext = c;
        clickCb = cb;
    }

  /*  public void setList(List<HotMovie> lists){
        list.addAll(lists);
    }*/

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (list.size()!=0){

            holder.img.setImageURI(Uri.parse(list.get(position%list.size()).getImageUrl()));
            holder.tv.setText(list.get(position%list.size()).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                    if (clickCb != null) {
                        clickCb.clickItem(position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface onItemClick {
        void clickItem(int pos);
    }
}
