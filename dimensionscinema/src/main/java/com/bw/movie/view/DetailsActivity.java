package com.bw.movie.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bw.movie.R;
import com.bw.movie.adapter.Btn2Adapter;
import com.bw.movie.adapter.Btn3Adapter;
import com.bw.movie.bean.MovieDetail;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.DetailMoviePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.jzvd.JZVideoPlayerStandard;
import me.jessyan.autosize.internal.CustomAdapt;

public class DetailsActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private SimpleDraweeView movieimage;
    private TextView moviename;
    private MovieDetail result;
    private RelativeLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int id = getIntent().getIntExtra("id", 0);
        DetailMoviePresenter presenter = new DetailMoviePresenter(new DetailData());
        presenter.request(1770, "15482453997081770",id);

        initView();
    }

    private void initView() {
        movieimage = findViewById(R.id.details_image);
        moviename = findViewById(R.id.details_name);
        findViewById(R.id.details_btn1).setOnClickListener(this);
        findViewById(R.id.details_btn2).setOnClickListener(this);
        findViewById(R.id.details_btn3).setOnClickListener(this);
        findViewById(R.id.details_btn4).setOnClickListener(this);
         ll = findViewById(R.id.ll);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.details_btn1:
                showBottomDialog();
                break;
            case R.id.details_btn2:
                showBottomDialog2();
                break;
            case R.id.details_btn3:
                showBottomDialog3();
                break;
            case R.id.details_btn4:
                break;
        }
    }

    private class DetailData implements DataCall<Result<MovieDetail>> {
        @Override
        public void success(Result<MovieDetail> data) {
         //   Toast.makeText(DetailsActivity.this, data.getResult().getPosterList().toString()+"", Toast.LENGTH_SHORT).show();
             result = data.getResult();
            movieimage.setImageURI(Uri.parse(result.getImageUrl()));
            moviename.setText(result.getName());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    private void showBottomDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_btn1,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialog.show();
        dialog.findViewById(R.id.btn1_dow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        SimpleDraweeView simpleDraweeView = dialog.findViewById(R.id.btn1_image);
        simpleDraweeView.setImageURI(Uri.parse(result.getImageUrl()));
        TextView details = dialog.findViewById(R.id.tn1_detail);
        details.setText(result.getSummary());
        TextView chan = dialog.findViewById(R.id.btn1_chan);
        chan.setText(result.getPlaceOrigin());
        TextView dao = dialog.findViewById(R.id.btn1_dao);
        dao.setText(result.getDirector());
        TextView lei = dialog.findViewById(R.id.btn1_lei);
        lei.setText(result.getMovieTypes());
        TextView shi = dialog.findViewById(R.id.btn1_shi);
        shi.setText(result.getDuration());
    }
    private void showBottomDialog3(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_btn3,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialog.show();
        dialog.findViewById(R.id.btn3_dow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        RecyclerView recyclerView3 = dialog.findViewById(R.id.btn3_recycler);
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView3.setLayoutManager(recyclerViewLayoutManager);
        Btn3Adapter btn3Adapter = new Btn3Adapter(DetailsActivity.this);
        recyclerView3.setAdapter(btn3Adapter);
        btn3Adapter.addList(result.getPosterList());
        btn3Adapter.notifyDataSetChanged();
    }
    private void showBottomDialog2(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_btn2,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialog.show();
        dialog.findViewById(R.id.btn2_dow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                JZVideoPlayerStandard.releaseAllVideos();
            }
        });
        RecyclerView recyclerView2 = dialog.findViewById(R.id.btn2_recycler);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(DetailsActivity.this);
         recyclerView2.setLayoutManager(layoutManager);
        Btn2Adapter btn2Adapter = new Btn2Adapter(DetailsActivity.this);
        recyclerView2.setAdapter(btn2Adapter);
        btn2Adapter.addList(result.getShortFilmList());
        btn2Adapter.notifyDataSetChanged();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                JZVideoPlayerStandard.releaseAllVideos();
            }
        });
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
