package com.bw.movie.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import com.bw.movie.presenter.HotMoviePresenter;
import com.bw.movie.presenter.ReleasePresenter;
import com.bw.movie.presenter.SoonPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class ListActivity extends AppCompatActivity implements CustomAdapt {

    private RecyclerView recyclerView;
    private RadioGroup group;
    private ListAdapter adapter;
    private MapView mMapView = null;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private TextView mdingwei;
    private int userId;
    private String sessionId;
    private List<AllUser> users = new ArrayList<>();
    private TextView tv_sou;
    private EditText et_sou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //绑定
        ButterKnife.bind(this);
        initView();
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0){
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        adapter = new ListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        onItemClick(adapter);
        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
        hotMoviePresenter.request(1770, "15482453997081770", 1, 500);
        group.check(group.getChildAt(0).getId());
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.list_mbutton1:
                        HotMoviePresenter hotMoviePresenter = new HotMoviePresenter(new HorMovieData());
                        hotMoviePresenter.request(userId, sessionId, 1, 500);

                        break;
                    case R.id.list_mbutton2:
                        ReleasePresenter releasePresenter = new ReleasePresenter(new ReleaseData());
                        releasePresenter.request(userId, sessionId, 1, 500);
                        break;
                    case R.id.list_mbutton3:
                        SoonPresenter soonPresenter = new SoonPresenter(new SoonData());
                        soonPresenter.request(userId, sessionId, 1, 500);
                        break;
                }
            }
        });
        //定位
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

    private void initView() {
        recyclerView = findViewById(R.id.list_recycer);
        group = findViewById(R.id.list_mRadio_cinema);
        mdingwei = findViewById(R.id.mdingwei);
        tv_sou = findViewById(R.id.tv_sou);
        et_sou = findViewById(R.id.et_sou);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            //定位
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
    //定位实现接口
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息
            mdingwei.setText(locationDescribe + addr);

        }
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    //热门电影
    private class HorMovieData implements DataCall<Result<List<HotMovie>>> {
        @Override
        public void success(Result<List<HotMovie>> data) {
            adapter.clearList();
            adapter.addList(data.getResult());
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
            adapter.notifyDataSetChanged();
        }
        @Override

        public void fail(ApiException e) {

        }
    }
    //点击实现搜索
    @OnClick(R.id.sou)
    public void sou() {
        tv_sou.setVisibility(View.VISIBLE);
        et_sou.setVisibility(View.VISIBLE);
    }

    //点击搜索隐藏
    @OnClick(R.id.tv_sou)
    public void et_sou() {
        et_sou.setVisibility(View.GONE);
        tv_sou.setVisibility(View.GONE);
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
}
