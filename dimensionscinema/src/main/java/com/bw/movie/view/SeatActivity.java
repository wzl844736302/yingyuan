package com.bw.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.MoviekeyPresenter;
import com.bw.movie.presenter.WxPresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.bw.movie.utils.util.MD5Utils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SeatActivity extends WDActivity implements View.OnClickListener {
    public SeatTable seatTableView;
    private int checked = 0;
    int id;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private TextView mname,address,mname1,begin1,end1,ting1;
    private double qian;
    private TextView sumQian;
    private SharedPreferences sp;
    private boolean xian;

    @Override
    protected void initView() {
        //初始化控件
        findViewById(R.id.seat_v).setOnClickListener(this);
        mname = findViewById(R.id.mname);
        address = findViewById(R.id.address);
        mname1 = findViewById(R.id.mname1);
        begin1 = findViewById(R.id.begin);
        end1 = findViewById(R.id.end);
        ting1 = findViewById(R.id.ting);
        sumQian = findViewById(R.id.sumqian);
        //接收值
        String name1 = getIntent().getStringExtra("name1");
        String address1 = getIntent().getStringExtra("address1");
        String dname = getIntent().getStringExtra("dname");
        String begin = getIntent().getStringExtra("begin");
        String end = getIntent().getStringExtra("end");
        String mz = getIntent().getStringExtra("mz");
         qian = getIntent().getDoubleExtra("qian", 0);

        id = getIntent().getIntExtra("id", 0);
        //设置值
        mname.setText(name1);
        address.setText(address1);
        mname1.setText(dname);
        begin1.setText(begin);
        end1.setText(end);
        ting1.setText(mz);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        seatTableView = findViewById(R.id.seat_seat);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(8);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            private NumberFormat nf;

            @Override
            public boolean isValidSeat(int row, int column) {
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                 nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(2);
                checked++;
                sumQian.setText(nf.format(qian*checked)+"");
            }

            @Override
            public void unCheck(int row, int column) {
                checked--;
                sumQian.setText(nf.format(qian*checked)+"");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 10);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seat;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seat_v:
                if (checked > 0) {
                    showBottomDialog();
                } else {
                    Toast.makeText(this, "请选择", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showBottomDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_wx, null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialog.show();
        RadioGroup group = dialog.findViewById(R.id.wx_group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.wx_but1:
                        Toast.makeText(SeatActivity.this, "微信", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wx_but2:
                        Toast.makeText(SeatActivity.this, "支付宝", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        dialog.findViewById(R.id.wx_qd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getSharedPreferences("login",Context.MODE_PRIVATE);
                xian = sp.getBoolean("xian", false);
                if (!xian){
                    Intent intent = new Intent(SeatActivity.this, LoginActivity.class);
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
                MoviekeyPresenter moviekeyPresenter = new MoviekeyPresenter(new KeyData());
                String s = MD5Utils.MD5(userId + "" + id + "" + checked + "movie");
                moviekeyPresenter.request(userId, sessionId, id, checked, s);
            }
        });
    }

    class KeyData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            WxPresenter wxPresenter = new WxPresenter(new WxData());
            wxPresenter.request(userId, sessionId, 1, data.getOrderId());
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    class WxData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(SeatActivity.this, null);
            // 将该app注册到微信
            msgApi.registerApp("wxb3852e6a6b7d9516");
            PayReq request = new PayReq();
            request.appId = data.getAppId();
            request.partnerId = data.getPartnerId();
            request.prepayId = data.getPrepayId();
            request.packageValue = data.getPackageValue();
            request.nonceStr = data.getNonceStr();
            request.timeStamp = data.getTimeStamp();
            request.sign = data.getSign();
            msgApi.sendReq(request);
        }
        @Override
        public void fail(ApiException e) {

        }
    }
}