package com.bw.movie.view;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.ListAdapter;;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.HotMovie;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.frag.FragCinema;
import com.bw.movie.presenter.CancelMoviePresenter;
import com.bw.movie.presenter.FocusMoviePresenter;
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.presenter.ReleasePresenter;
import com.bw.movie.presenter.SoonPresenter;
import com.bw.movie.utils.jilei.WDActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends WDActivity implements ListAdapter.OnItemBack {

    private RecyclerView recyclerView;
    private RadioGroup group;
    private ListAdapter adapter;
    private int userId;
    private String sessionId;
    private List<AllUser> users = new ArrayList<>();
    private RadioButton list_mbutton1,list_mbutton2,list_mbutton3;
    private int a;
    private FocusMoviePresenter moviePresenter;
    private CancelMoviePresenter cancelMoviePresenter;
    private SharedPreferences sp;
    private boolean xian;
    private LinearLayout mlinear;
    private ObjectAnimator animator;
    //百度定位
    private MapView mMapView = null;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private TextView mdingwei;
    @Override
    protected void initView() {
        //绑定
        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.list_recycer);
        group = findViewById(R.id.list_mRadio_cinema);
        list_mbutton1 = findViewById(R.id.list_mbutton1);
        list_mbutton2 = findViewById(R.id.list_mbutton2);
        list_mbutton3 = findViewById(R.id.list_mbutton3);
        mlinear = findViewById(R.id.mlinear);
        mdingwei = findViewById(R.id.mdingwei);
        findViewById(R.id.list_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0){
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        Intent intent = getIntent();
        a = intent.getIntExtra("a",0);

        adapter = new ListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        onItemClick(adapter);
        int id = getIntent().getIntExtra("id", 0);
        switch (id){
            case 0:
                list_mbutton1.setTextColor(Color.WHITE);
                list_mbutton2.setTextColor(Color.BLACK);
                list_mbutton3.setTextColor(Color.BLACK);
                HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
                hotMoviePresenter.request(userId, sessionId, 1, 500);
                break;
            case 1:
                list_mbutton1.setTextColor(Color.BLACK);
                list_mbutton2.setTextColor(Color.WHITE);
                list_mbutton3.setTextColor(Color.BLACK);
                SoonPresenter soonPresenter = new SoonPresenter(new SoonData());
                soonPresenter.request(userId, sessionId, 1, 500);
                break;
            case 2:
                list_mbutton1.setTextColor(Color.BLACK);
                list_mbutton2.setTextColor(Color.BLACK);
                list_mbutton3.setTextColor(Color.WHITE);
                ReleasePresenter releasePresenter = new ReleasePresenter(new ReleaseData());
                releasePresenter.request(userId, sessionId, 1, 500);
                break;
        }

        group.check(group.getChildAt(id).getId());
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.list_mbutton1:
                        list_mbutton1.setTextColor(Color.WHITE);
                        list_mbutton2.setTextColor(Color.BLACK);
                        list_mbutton3.setTextColor(Color.BLACK);
                        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
                        hotMoviePresenter.request(userId, sessionId, 1, 500);
                        break;
                    case R.id.list_mbutton2:
                        list_mbutton1.setTextColor(Color.BLACK);
                        list_mbutton2.setTextColor(Color.WHITE);
                        list_mbutton3.setTextColor(Color.BLACK);

                        SoonPresenter soonPresenter = new SoonPresenter(new SoonData());
                        soonPresenter.request(userId, sessionId, 1, 500);
                        break;
                    case R.id.list_mbutton3:
                        list_mbutton1.setTextColor(Color.BLACK);
                        list_mbutton2.setTextColor(Color.BLACK);
                        list_mbutton3.setTextColor(Color.WHITE);
                        ReleasePresenter releasePresenter = new ReleasePresenter(new ReleaseData());
                        releasePresenter.request(userId, sessionId, 1, 500);
                        break;
                }
            }
        });
        adapter.setItemBack(this);

        //调用百度定位的方法
        initData();
       findViewById(R.id.miv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                onResume();
            }
        });
    }
    //点击实现搜索
    @OnClick(R.id.sou)
    public void sou1() {
        animator = ObjectAnimator.ofFloat(mlinear, "translationX", 30f, -550f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    //点击搜索隐藏
    @OnClick(R.id.tv_sou)
    public void tvsou() {
        animator = ObjectAnimator.ofFloat(mlinear, "translationX", -560f, 0f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    //是否关注电影
    @Override
    public void back(int id, int ischeck) {
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        xian = sp.getBoolean("xian", false);
        if (!xian){
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ListActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.clear();
        users.addAll(allUserDao.loadAll());
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        if (ischeck == 1){
            moviePresenter = new FocusMoviePresenter(new MovieCalls());
            moviePresenter.request(userId,sessionId,id);
        }else {
            cancelMoviePresenter = new CancelMoviePresenter(new CancelCall());
            cancelMoviePresenter.request(userId,sessionId,id);
        }
    }
    //实现取消电影接口
    class CancelCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(ListActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //实现关注电影接口
    class MovieCalls implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(ListActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //热门电影
    private class HorMovieData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            adapter.clearList();
            adapter.addList(data.getResult());
            initData();
            adapter.notifyDataSetChanged();
        }
        @Override
        public void fail(ApiException e) {
        }
    }
    //正在热销
    private class ReleaseData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            adapter.clearList();
            adapter.addList(data.getResult());
            initData();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //即将上映
    private class SoonData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            adapter.clearList();
            adapter.addList(data.getResult());
            initData();
            adapter.notifyDataSetChanged();
        }
        @Override

        public void fail(ApiException e) {

        }
    }

    private void onItemClick(final ListAdapter adapter){
        adapter.setOnclickItem(new ListAdapter.OnclickItem() {
            @Override
            public void OnclickItem(View view) {
                List<HotMovie> list = adapter.getList();
                Intent intent = new Intent(ListActivity.this,DetailsActivity.class);
                intent.putExtra("id",list.get(recyclerView.getChildAdapterPosition(view)).getId());
                startActivity(intent);
             }
        });
    }
    //百度定位
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String city = location.getCity();
         /*   String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息*/
            if (city != null | city.equals("")) {
                mdingwei.setText(city);
            }
        }
    }
    //定位的方法
    private void initData() {
            if (ContextCompat.checkSelfPermission(ListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //权限还没有授予，需要在这里写申请权限的代码
                ActivityCompat.requestPermissions(ListActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_PHONE_STATE}, 100);
            } else {
                mLocationClient = new LocationClient(this);
                //声明LocationClient类
                mLocationClient.registerLocationListener(myListener);
                //注册监听函数
                LocationClientOption option = new LocationClientOption();
                option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
                //可选，是否需要位置描述信息，默认为不需要，即参数为false
                //如果开发者需要获得当前点的位置信息，此处必须为true
                option.setIsNeedLocationDescribe(true);
                //可选，设置是否需要地址信息，默认不需要
                option.setIsNeedAddress(true);
                //可选，默认false,设置是否使用gps
                option.setOpenGps(true);
                //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
                option.setLocationNotify(true);
                mLocationClient.setLocOption(option);
                mLocationClient.start();
            }
    }
    //动态权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            mLocationClient = new LocationClient(this);
            //声明LocationClient类
            mLocationClient.registerLocationListener(myListener);
            //注册监听函数
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
            //可选，是否需要位置描述信息，默认为不需要，即参数为false
            //如果开发者需要获得当前点的位置信息，此处必须为true
            option.setIsNeedLocationDescribe(true);
            //可选，设置是否需要地址信息，默认不需要
            option.setIsNeedAddress(true);
            //可选，默认false,设置是否使用gps
            option.setOpenGps(true);
            //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
            option.setLocationNotify(true);
            mLocationClient.setLocOption(option);
            mLocationClient.start();
        }
    }
}
