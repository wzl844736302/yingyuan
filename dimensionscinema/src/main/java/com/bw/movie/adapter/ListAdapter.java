package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.HotMovie;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {


    private List<HotMovie> mList = new ArrayList<>();
    private Context context;

    private OnclickItem onclickItem;
    private LayoutInflater from;

    public void setOnclickItem(OnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }

    public ListAdapter(Context context) {
        this.context = context;
        from = LayoutInflater.from(context);
    }
    private OnItemBack itemBack;
    public interface OnItemBack{
        void back(int id,int ischeck);
    }
    public void setItemBack(OnItemBack itemBack) {
        this.itemBack = itemBack;
    }

    public void addList(List<HotMovie> data) {
        mList.addAll(data);
    }

    public List<HotMovie> getList() {
        return mList;
    }

    public void clearList() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void delList(int i) {
        mList.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = from.inflate(R.layout.list_item, viewGroup, false);
        MyHodler myHodler = new MyHodler(view);
        return myHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        MyHodler myHodler = new MyHodler(viewHolder.itemView);
        final HotMovie hotMovie = mList.get(i);
        myHodler.imageView.setImageURI(Uri.parse(mList.get(i).getImageUrl()));
        myHodler.name.setText(mList.get(i).getName());
        myHodler.cont.setText(mList.get(i).getSummary());
        final int id = mList.get(i).getId();
        if (onclickItem != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclickItem.OnclickItem(view);
                }
            });
        }
        myHodler.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                hotMovie.setIscheck(checked?1:0);
                itemBack.back(id,hotMovie.getIscheck());
            }
        });
        if (hotMovie.getIscheck()==0){
            myHodler.checkBox.setChecked(false);
        }else {
            myHodler.checkBox.setChecked(true);
        }
        myHodler.checkBox.setChecked(hotMovie.isFollowMovie()==1?true:false);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHodler extends RecyclerView.ViewHolder {

        private TextView name, cont;
        private ImageView imageView;
        CheckBox checkBox;

        public MyHodler(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_item_name);
            imageView = itemView.findViewById(R.id.list_item_iv);
            cont = itemView.findViewById(R.id.list_item_cont);
            checkBox = itemView.findViewById(R.id.lsit_item_cb);
        }
    }

    public interface OnclickItem {
        void OnclickItem(View view);
    }
}
