package com.bw.movie.frag;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bw.movie.adapter.RecommendAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.Recommend;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.FuJinPresenter;
import com.bw.movie.presenter.RecommendPresenter;
import com.bw.movie.utils.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

public class FragCinema extends Fragment implements CustomAdapt {

    private RadioGroup mradio_cinema;
    private Button mbutton1;
    private Button mbutton2;
    private RecyclerView mrecycler_cinema;
    private RecommendPresenter recommendPresenter;
    private RecommendAdapter recommendAdapter;
    private FuJinPresenter fuJinPresenter;
    private Unbinder bind;
    private TextView tv_sou;
    private EditText et_sou;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private TextView mdingwei;
    private MapView mMapView = null;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private double latitude;
    private double longitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cinema, container, false);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //绑定
        bind = ButterKnife.bind(this, view);
        //初始化控件
        mradio_cinema = view.findViewById(R.id.mRadio_cinema);
        mbutton1 = view.findViewById(R.id.mbutton1);
        mbutton2 = view.findViewById(R.id.mbutton2);
        mrecycler_cinema = view.findViewById(R.id.mrecycler_cinema);
        tv_sou = view.findViewById(R.id.tv_sou);
        et_sou = view.findViewById(R.id.et_sou);
        mdingwei = view.findViewById(R.id.mdingwei);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //设置适配器
        recommendAdapter = new RecommendAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecycler_cinema.setLayoutManager(linearLayoutManager);
        mrecycler_cinema.setAdapter(recommendAdapter);
        //设置数据
        recommendPresenter = new RecommendPresenter(new RecommendCall());
        recommendPresenter.request(userId, sessionId, 1, 10);
        //默认选中第一个
        mradio_cinema.check(mradio_cinema.getChildAt(0).getId());
        mbutton1.setTextColor(Color.WHITE);
        //点击选中切换
        mradio_cinema.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mbutton1:
                        recommendAdapter.clear();
                        mbutton1.setTextColor(Color.WHITE);
                        mbutton2.setTextColor(Color.BLACK);
                        recommendPresenter = new RecommendPresenter(new RecommendCall());
                        recommendPresenter.request(userId, sessionId, 1, 10);
                        break;
                    case R.id.mbutton2:
                        recommendAdapter.clear();
                        mbutton1.setTextColor(Color.BLACK);
                        mbutton2.setTextColor(Color.WHITE);
                        fuJinPresenter = new FuJinPresenter(new FuJinCall());
                        fuJinPresenter.request(userId, sessionId, longitude+"", latitude+"", 1, 10);
                        break;
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
                }, 100);
            }else {
                initData();
            }
        }else {
            initData();
        }
        return view;
    }
    public void initData(){
        //定位
        mLocationClient = new LocationClient(getActivity());
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            //定位
            mLocationClient = new LocationClient(getActivity());
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

    //实现附近影院接口
    class FuJinCall implements DataCall<Result<List<Recommend>>> {
        @Override
        public void success(Result<List<Recommend>> data) {
            if (data.getStatus().equals("0000")) {
                List<Recommend> result = data.getResult();
                recommendAdapter.addAll(result);
                recommendAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }

    //实现推荐影院接口
    class RecommendCall implements DataCall<Result<List<Recommend>>> {
        @Override
        public void success(Result<List<Recommend>> data) {
            if (data.getStatus().equals("0000")) {
                List<Recommend> result = data.getResult();
                recommendAdapter.addAll(result);
                recommendAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
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
            latitude = location.getLatitude();
            longitude = location.getLongitude();
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

    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }
}
