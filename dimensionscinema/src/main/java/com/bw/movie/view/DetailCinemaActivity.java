package com.bw.movie.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.Btn2Adapter;
import com.bw.movie.adapter.Btn4Adapter;
import com.bw.movie.adapter.DetailCinemaAdapter;
import com.bw.movie.adapter.ScheduleAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.CinemaInfo;
import com.bw.movie.bean.Comment;
import com.bw.movie.bean.MovieList;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.ScheduleList;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.CinemaInfoPresenter;
import com.bw.movie.presenter.CinemaplPresenter;
import com.bw.movie.presenter.CommentGreatPresenter;
import com.bw.movie.presenter.DetailCinemaPresenter;
import com.bw.movie.presenter.SchedulePresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class DetailCinemaActivity extends WDActivity {

    private DetailCinemaPresenter cinemaPresenter;
    private SimpleDraweeView msim;
    private TextView mname;
    private TextView mweizhi;
    private RecyclerCoverFlow list;
    private DetailCinemaAdapter detailCinemaAdapter;
    private RecyclerView mrecycler;
    private SchedulePresenter schedulePresenter;
    private int id;
    private ScheduleAdapter scheduleAdapter;
    private List<MovieList> result;
    private int id1;
    private ImageView mreturn;
    private List<ScheduleList> result1;
    private LinearLayout linearLayout;
    private RecyclerView drecycler;
    private View vPl;
    private View vXq;
    private int userId;
    private String sessionId;
    private List<AllUser> users = new ArrayList<>();
    private TextView wz;
    private TextView dt;
    private TextView dh;
    private TextView zn;
    private TextView gj;
    private TextView zj;
    private RecyclerView myRecyc;
    private Btn4Adapter btn4Adapter;
    private CommentGreatPresenter commentGreatPresenter;
    @Override
    protected void initView() {
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //初始化控件
        msim = findViewById(R.id.msim);
        mname = findViewById(R.id.mname);
        mweizhi = findViewById(R.id.mweizhi);
        list = findViewById(R.id.list);
        mrecycler = findViewById(R.id.mrecycler);
        mreturn = findViewById(R.id.mreturn);
        //接收值
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String tupian = intent.getStringExtra("tupian");
        final String name = intent.getStringExtra("name");
        final String weizhi = intent.getStringExtra("weizhi");
        //设置值
        msim.setImageURI(tupian);

        mname.setText(name);
        mweizhi.setText(weizhi);
        //设置适配器
        detailCinemaAdapter = new DetailCinemaAdapter(this);
        list.setAdapter(detailCinemaAdapter);

        //获取数据
        cinemaPresenter = new DetailCinemaPresenter(new DetailCall());
        cinemaPresenter.request(id);
        //设置适配器
        scheduleAdapter = new ScheduleAdapter(DetailCinemaActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailCinemaActivity.this);
        mrecycler.setLayoutManager(layoutManager);
        mrecycler.setAdapter(scheduleAdapter);
        //点击按钮返回
        mreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        scheduleAdapter.setBacks(new ScheduleAdapter.CallBacks() {
            @Override
            public void call(int pos) {
                //跳转
                Intent intent = new Intent(DetailCinemaActivity.this, SeatActivity.class);
                intent.putExtra("name1",name);
                intent.putExtra("address1",weizhi);
                intent.putExtra("dname",result.get(pos).getName());
                intent.putExtra("begin",result1.get(pos).getBeginTime());
                intent.putExtra("end",result1.get(pos).getEndTime());
                intent.putExtra("mz",result1.get(pos).getScreeningHall());
                startActivity(intent);
            }
        });
        msim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog2();
            }
        });
        /*btn4Adapter.setCallLove1(this);*/
      /*  btn4Adapter.setCallLove1(new Btn4Adapter.CallLove1() {
            @Override
            public void love(int id, int ischeck) {
                if (ischeck == 1){
                    Toast.makeText(DetailCinemaActivity.this, "111", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_cinema;
    }

   /* @Override
    public void love(int id, int ischeck) {
        if (ischeck == 1){
            commentGreatPresenter = new CommentGreatPresenter(new Great());
            commentGreatPresenter.request(userId,sessionId,id);
        }
    }*/

   /* //实现影院点赞接口
    class Great implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(DetailCinemaActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }*/
    //实现当前电影列表
    class DetailCall implements DataCall<Result<List<MovieList>>> {
        @Override
        public void success(Result<List<MovieList>> data) {
            if (data.getStatus().equals("0000")) {
                result = data.getResult();
                detailCinemaAdapter.addAll(result);
                id1 = result.get(0).getId();
                list.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                    @Override
                    public void onItemSelected(int position) {
                        id1 = result.get(position).getId();
                        schedulePresenter = new SchedulePresenter(new ScheduleCall());
                        schedulePresenter.request(DetailCinemaActivity.this.id, id1);
                    }
                });
                //获取数据
                schedulePresenter = new SchedulePresenter(new ScheduleCall());
                schedulePresenter.request(id, id1);
                detailCinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //实现电影排期列表
    class ScheduleCall implements DataCall<Result<List<ScheduleList>>> {

        @Override
        public void success(Result<List<ScheduleList>> data) {
            if (data.getStatus().equals("0000")) {
                result1 = data.getResult();
                scheduleAdapter.addAll(result1);
                scheduleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    private void showBottomDialog2(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_cinema,null);
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
        dialog.findViewById(R.id.dc_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        linearLayout = dialog.findViewById(R.id.dc_xx);
        drecycler = dialog.findViewById(R.id.dc_recyc);
        vPl = dialog.findViewById(R.id.dc_v_pl);
        vXq = dialog.findViewById(R.id.dc_v_xq);
        dialog.findViewById(R.id.dc_pl).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              linearLayout.setVisibility(View.GONE);
              drecycler.setVisibility(View.VISIBLE);
              vPl.setVisibility(View.VISIBLE);
              vXq.setVisibility(View.INVISIBLE);
          }
      });
        dialog.findViewById(R.id.dc_xq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                drecycler.setVisibility(View.GONE);
                vPl.setVisibility(View.INVISIBLE);
                vXq.setVisibility(View.VISIBLE);
            }
        });
        CinemaInfoPresenter cinemaInfoPresenter = new CinemaInfoPresenter(new CinemaInfoData());
        cinemaInfoPresenter.request(userId,sessionId,id1);
        wz = dialog.findViewById(R.id.dc_wz);
        dt = dialog.findViewById(R.id.dc_dt);
        dh = dialog.findViewById(R.id.dc_dh);
        zn = dialog.findViewById(R.id.dc_zn);
        myRecyc = dialog.findViewById(R.id.dc_recyc);
        CinemaplPresenter cinemaplPresenter = new CinemaplPresenter(new Cinemapl());
        cinemaplPresenter.request(userId,sessionId,id1);
        btn4Adapter = new Btn4Adapter(this);
        LinearLayoutManager layoutManager = new GridLayoutManager(this,LinearLayoutManager.VERTICAL);
        myRecyc.setLayoutManager(layoutManager);
        myRecyc.setAdapter(btn4Adapter);
    }
    private class CinemaInfoData implements DataCall<Result<CinemaInfo>> {
        @Override
        public void success(Result<CinemaInfo> data) {
         //  Toast.makeText(DetailCinemaActivity.this, data.getResult().toString()+"", Toast.LENGTH_SHORT).show();
            wz.setText(data.getResult().getAddress());
            dh.setText(data.getResult().getPhone()+"");
            zn.setText("乘车路线");
            dt.setText(data.getResult().getVehicleRoute());
        }
        @Override
        public void fail(ApiException e) {

        }
    }

    private class Cinemapl implements DataCall<Result<List<Comment>>>  {
        @Override
        public void success(Result<List<Comment>> data) {
            if (data.getStatus().equals("0000")){
                List<Comment> result = data.getResult();
                btn4Adapter.addList(result);
                btn4Adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
