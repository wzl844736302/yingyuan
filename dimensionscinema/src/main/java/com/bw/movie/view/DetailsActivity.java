package com.bw.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.Btn2Adapter;
import com.bw.movie.adapter.Btn3Adapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.adapter.Btn4Adapter;
import com.bw.movie.bean.MovieComment;
import com.bw.movie.bean.MovieDetail;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.CommentPresenter;
import com.bw.movie.presenter.DetailMoviePresenter;
import com.bw.movie.presenter.MovieCommentPresenter;
import com.bw.movie.presenter.MovieGreatPresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.util.List;
import java.util.ArrayList;
import cn.jzvd.JZVideoPlayerStandard;


public class DetailsActivity extends WDActivity implements View.OnClickListener{

    private int userId;
    private String sessionId;
    private SimpleDraweeView movieimage;
    private TextView moviename;
    private MovieDetail result;
    private RelativeLayout ll;
    private int id;
    private Btn4Adapter btn4Adapter;
    private String name;
    private List<AllUser> users = new ArrayList<>();
    private LinearLayout pinglun;
    private ImageView xie;
    private EditText plEd;
    private MovieGreatPresenter movieGreatPresenter;
    @Override
    protected void initView() {
        movieimage = findViewById(R.id.details_image);
        moviename = findViewById(R.id.details_name);
        findViewById(R.id.details_btn1).setOnClickListener(this);
        findViewById(R.id.details_btn2).setOnClickListener(this);
        findViewById(R.id.details_btn3).setOnClickListener(this);
        findViewById(R.id.details_btn4).setOnClickListener(this);
        findViewById(R.id.details_return).setOnClickListener(this);
        ll = findViewById(R.id.ll);
        findViewById(R.id.goupiao).setOnClickListener(this);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }

        id = getIntent().getIntExtra("id", 0);
        DetailMoviePresenter presenter = new DetailMoviePresenter(new DetailData());
        presenter.request(userId, sessionId, id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                showBottomDialog4();
                break;
            case R.id.details_return:
                finish();
                break;
            case R.id.goupiao:
                //跳转
                Intent intent = new Intent(DetailsActivity.this, TicketActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
    }

    private class DetailData implements DataCall<Result<MovieDetail>> {
        @Override
        public void success(Result<MovieDetail> data) {
            result = data.getResult();
            movieimage.setImageURI(Uri.parse(result.getImageUrl()));
            name = result.getName();
            moviename.setText(name);
            id = result.getId();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private void showBottomDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_btn1, null);
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

    private void showBottomDialog3() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_btn3, null);
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

    private void showBottomDialog2() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_btn2, null);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this);
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

    private void showBottomDialog4() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_btn4, null);
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
        dialog.findViewById(R.id.btn4_dow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        pinglun = dialog.findViewById(R.id.btn4_ping);
        plEd = dialog.findViewById(R.id.btn4_pinged);
        xie = dialog.findViewById(R.id.btn4_xie);
        xie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinglun.setVisibility(View.VISIBLE);
                xie.setVisibility(View.GONE);
            }
        });
        dialog.findViewById(R.id.btn4_fs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = plEd.getText().toString().trim();
                MovieCommentPresenter movieCommentPresenter = new MovieCommentPresenter(new plData());
                movieCommentPresenter.request(userId, sessionId, id, trim);
                plEd.setText("");
                CommentPresenter commentPresenter = new CommentPresenter(new CommentData());
                commentPresenter.request(userId, sessionId, id);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
        XRecyclerView recyclerView = dialog.findViewById(R.id.btn4_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        btn4Adapter = new Btn4Adapter(DetailsActivity.this);
        recyclerView.setAdapter(btn4Adapter);
         //初始化回调接口
        btn4Adapter.setMovieGreat1(new Btn4Adapter.MovieGreat() {
            @Override
            public void great(int id, int ischeck) {
                if (ischeck == 0) {
                    movieGreatPresenter = new MovieGreatPresenter(new MovieGreat());
                    movieGreatPresenter.request(userId,sessionId,id);
                }
            }
        });
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        List<AllUser> users = allUserDao.loadAll();
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        CommentPresenter commentPresenter = new CommentPresenter(new CommentData());
        commentPresenter.request(userId, sessionId, id);
    }
    //实现影片评论点赞接口
    class MovieGreat implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(DetailsActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    private class CommentData implements DataCall<Result<List<MovieComment>>> {
        @Override
        public void success(Result<List<MovieComment>> data) {
            btn4Adapter.clearList();
            btn4Adapter.addList(data.getResult());
            btn4Adapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class plData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(DetailsActivity.this, data.getMessage() + "", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
