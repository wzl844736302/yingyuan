package com.bw.movie.view;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
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
import com.bw.movie.utils.util.MD5Utils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

public class SeatActivity extends AppCompatActivity implements View.OnClickListener {
    public SeatTable seatTableView;
    private int checked = 0;
    int id;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size() > 0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }

        id = getIntent().getIntExtra("id", 0);
        Toast.makeText(this, id + "排期表", Toast.LENGTH_SHORT).show();
        seatTableView = findViewById(R.id.seat_seat);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(8);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

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

                checked++;
            }

            @Override
            public void unCheck(int row, int column) {
                checked--;
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 10);
        initView();
    }

    private void initView() {
        findViewById(R.id.seat_v).setOnClickListener(this);
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
                switch (radioGroup.getId()) {
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
                Toast.makeText(SeatActivity.this, id + "", Toast.LENGTH_SHORT).show();

                MoviekeyPresenter moviekeyPresenter = new MoviekeyPresenter(new KeyData());
                String s = MD5Utils.MD5(userId + "" + id + "" + checked + "movie");
                moviekeyPresenter.request(userId, sessionId, id, checked, s);
            }
        });
    }

    class KeyData implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(SeatActivity.this, data.getOrderId() + "", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SeatActivity.this, data.getMessage() + "", Toast.LENGTH_SHORT).show();
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