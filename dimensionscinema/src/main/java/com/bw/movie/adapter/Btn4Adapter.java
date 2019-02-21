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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieComment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Btn4Adapter extends RecyclerView.Adapter {

    private List<MovieComment> mList = new ArrayList<>();
    private Context context;
    private LayoutInflater from;
    private OnclickItem1 onclickItem;
    public Btn4Adapter(Context context) {
        this.context = context;
        from = LayoutInflater.from(context);
    }
    public void setOnclickItem(OnclickItem1 onclickItem) {
        this.onclickItem = onclickItem;
    }
    private MovieGreat movieGreat;
    public interface MovieGreat{
        void great(int id,int ischeck);
    }
    public void setMovieGreat1(MovieGreat movieGreat) {
        this.movieGreat = movieGreat;
    }

    public void addList(List<MovieComment> data) {
        mList.addAll(data);
    }

    public List<MovieComment> getList() {
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
        View view = from.inflate(R.layout.item_btn4, viewGroup, false);
        MyHodler myHodler = new MyHodler(view);
        return myHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final MyHodler myHodler = (MyHodler) viewHolder;
        final MovieComment comment = mList.get(i);
        Date date = new Date(mList.get(i).getCommentTime());
        SimpleDateFormat fomat2 = new SimpleDateFormat("MM-dd  hh:mm");
        String mydate = fomat2.format(date);
        myHodler.time.setText(mydate);
        myHodler.detail.setText(mList.get(i).getCommentContent());
        myHodler.imageView.setImageURI(Uri.parse(mList.get(i).getCommentHeadPic()));
        myHodler.name.setText(mList.get(i).getCommentUserName());
        myHodler.good.setText(mList.get(i).getGreatNum() + "");
        myHodler.comentext.setText(mList.get(i).getReplyNum() + "");
        final int id = mList.get(i).getCommentId();
        if (onclickItem != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclickItem.OnclickItem1(view,mList.get(i).getCommentUserName());

                }
            });
        }
        //点赞
        myHodler.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox box = (CheckBox) v;
                boolean checked = box.isChecked();
                comment.setIscheck(checked?1:0);
                int greatNum = comment.getGreatNum();
                int i1 = greatNum + 1;
                comment.setGreatNum(i1);
                movieGreat.great(id,comment.getIsGreat());
                comment.setIsGreat(1);
                notifyDataSetChanged();
            }
        });
        if (comment.getIscheck()==0){
            myHodler.checkBox.setChecked(false);
        }else {
            myHodler.checkBox.setChecked(true);
        }
        myHodler.checkBox.setChecked(comment.getIsGreat()==1?true:false);
        if (comment.getIsGreat()==1?true:false){
            myHodler.checkBox.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHodler extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageView;
        private TextView detail, name, time, comentext, good;
        private CheckBox checkBox;
        private ImageView coment;
        private  ImageView imageCom;
        public MyHodler(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_btn4_image);
            detail = itemView.findViewById(R.id.item_btn4_detail);
            name = itemView.findViewById(R.id.item_btn4_name);
            time = itemView.findViewById(R.id.item_btn4_time);
            good = itemView.findViewById(R.id.mText);
            checkBox = itemView.findViewById(R.id.item_btn4_good);
            coment = itemView.findViewById(R.id.item_btn4_coment);
            comentext = itemView.findViewById(R.id.item_btn4_coment_sum);
            imageCom = itemView.findViewById(R.id.item_btn4_coment);
        }
    }
    public  interface OnclickItem1{
        void OnclickItem1(View view,String s);
    }
}