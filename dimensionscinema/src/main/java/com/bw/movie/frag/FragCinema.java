package com.bw.movie.frag;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bw.movie.presenter.CancelFocusPresenter;
import com.bw.movie.presenter.FocusOnPresenter;
import com.bw.movie.presenter.FuJinPresenter;
import com.bw.movie.presenter.RecommendPresenter;
import com.bw.movie.utils.util.UIUtils;
import com.bw.movie.view.LoginActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;

public class FragCinema extends Fragment implements RecommendAdapter.CallLove {

    private RadioGroup mradio_cinema;
    private Button mbutton1;
    private Button mbutton2;
    private RecyclerView mrecycler_cinema;
    private RecommendPresenter recommendPresenter;
    private RecommendAdapter recommendAdapter;
    private FuJinPresenter fuJinPresenter;
    private Unbinder bind;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private FocusOnPresenter focusOnPresenter;
    private CancelFocusPresenter cancelFocusPresenter;
    private boolean xian;
    private LinearLayout mlinear;
    private ObjectAnimator animator;
    //百度定位
    private MapView mMapView = null;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private TextView mdingwei;
    private double longitude;
    private double latitude;
    private ImageView miv;

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
        mlinear = view.findViewById(R.id.mlinear);
        mdingwei = view.findViewById(R.id.mdingwei);
        miv = view.findViewById(R.id.miv);
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
        recommendAdapter.setCallLove(this);
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
                        mbutton1.setTextColor(Color.WHITE);
                        mbutton2.setTextColor(Color.BLACK);
                        recommendPresenter = new RecommendPresenter(new RecommendCall());
                        recommendPresenter.request(userId, sessionId, 1, 10);
                        break;
                    case R.id.mbutton2:
                        mbutton1.setTextColor(Color.BLACK);
                        mbutton2.setTextColor(Color.WHITE);
                        fuJinPresenter = new FuJinPresenter(new FuJinCall());
                        fuJinPresenter.request(userId, sessionId, longitude+"", latitude+"", 1, 10);
                        break;
                }
            }
        });
        //调用定位的方法
        initData();
      /* miv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                onResume();
            }
        });*/
        return view;
    }

    //点击实现搜索
    @OnClick(R.id.sou)
    public void sou() {
        animator = ObjectAnimator.ofFloat(mlinear, "translationX", 30f, -550f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    //点击搜索隐藏
    @OnClick(R.id.tv_sou)
    public void et_sou() {
        animator = ObjectAnimator.ofFloat(mlinear, "translationX", -560f, 0f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
    //关注
    @Override
    public void love(int id, int ischeck) {
        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        xian = sp.getBoolean("xian", false);
        if (!xian){
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
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
            focusOnPresenter = new FocusOnPresenter(new FoucsOnCall());
            focusOnPresenter.request(userId,sessionId,id);
        }else {
           cancelFocusPresenter = new CancelFocusPresenter(new CancelCall());
           cancelFocusPresenter.request(userId,sessionId,id);
        }
    }
    //实现取消关注影院
    class CancelCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //实现关注影院接口
    class FoucsOnCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //实现附近影院接口
    class FuJinCall implements DataCall<Result<List<Recommend>>> {
        @Override
        public void success(Result<List<Recommend>> data) {
            if (data.getStatus().equals("0000")) {
                recommendAdapter.clear();
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
                recommendAdapter.clear();
                mradio_cinema.check(mradio_cinema.getChildAt(0).getId());
                mbutton1.setTextColor(Color.WHITE);
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

    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        users.clear();
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        recommendPresenter = new RecommendPresenter(new RecommendCall());
        recommendPresenter.request(userId, sessionId, 1, 10);
    }
    //百度定位
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String city = location.getCity();
            longitude = location.getLongitude();
            latitude = location.getLatitude();
         /*   String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息*/
            if (city != null | city.equals("")) {
                mdingwei.setText(city);
            }
        }
    }
    //定位的方法
    private void initData() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_PHONE_STATE}, 100);
        } else {
            mLocationClient = new LocationClient(getContext());
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
            mLocationClient = new LocationClient(getContext());
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

   @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
