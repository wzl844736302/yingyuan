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

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.Comment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Btn4Adapter extends RecyclerView.Adapter {


    private List<Comment> mList = new ArrayList<>();
    private Context context;


    private OnclickItem onclickItem;
    private  LayoutInflater from;

    public void setOnclickItem(OnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }

    public Btn4Adapter(Context context) {
        this.context = context;
        from = LayoutInflater.from(context);
    }
    public void addList(List<Comment> data){
        mList.addAll(data);
    }
    public List<Comment> getList(){
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
        View view = from.inflate(R.layout.item_btn4,viewGroup,false);
        MyHodler myHodler = new MyHodler(view);
        return myHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder  viewHolder,final int  i) {
        MyHodler myHodler = new MyHodler(viewHolder.itemView);
        Date date = new Date((long)mList.get(i).getCommentTime());
        SimpleDateFormat fomat2 = new SimpleDateFormat("MM-dd  hh:mm");
        String mydate = fomat2.format(date);
        myHodler.time.setText(mydate);
        myHodler.detail.setText(mList.get(i).getMovieComment());
        myHodler.imageView.setImageURI(Uri.parse(mList.get(i).getCommentHeadPic()));
        myHodler.name.setText(mList.get(i).getCommentUserName());
        myHodler.good.setText(mList.get(i).getGreatNum()+"");
        myHodler.comentext.setText(mList.get(i).getReplyNum()+"");
        if (onclickItem != null){
           viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onclickItem.OnclickItem(view);
               }
           });
        }
        }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHodler extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageView;
        private  TextView detail,name,time,comentext;
        private CheckBox good;
        private ImageView coment;
        public MyHodler(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_btn4_image);
             detail = itemView.findViewById(R.id.item_btn4_detail);
             name = itemView.findViewById(R.id.item_btn4_name);
             time = itemView.findViewById(R.id.item_btn4_time);
             good = itemView.findViewById(R.id.item_btn4_good);
             coment = itemView.findViewById(R.id.item_btn4_coment);
             comentext = itemView.findViewById(R.id.item_btn4_coment_sum);
        }
    }
   public  interface OnclickItem{
        void OnclickItem(View view);
    }
}
