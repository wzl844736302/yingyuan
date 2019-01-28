package com.bw.movie.view;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.BuyTicketAdapter;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.BuyTicketList;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.BuyTicketPresenter;
import com.bw.movie.presenter.MoviekeyPresenter;
import com.bw.movie.presenter.WxPresenter;
import com.bw.movie.utils.util.MD5Utils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyTicketActivity extends AppCompatActivity implements BuyTicketAdapter.SingleBack {
    private BuyTicketPresenter ticketPresenter;
    private List<AllUser> users = new ArrayList<>();
    private int userId;
    private String sessionId;
    private RecyclerView mrecyclerbuy;
    private int status = 1;
    private BuyTicketAdapter buyTicketAdapter;
    private RadioGroup mradio_cinema;
    private Button mbutton1;
    private Button mbutton2;
    private ImageView mreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        //绑定
        ButterKnife.bind(this);
        //查询数据库
        AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
        users.addAll(allUserDao.loadAll());
        if (users.size()>0) {
            AllUser allUser = users.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //初始化控件
        mradio_cinema = findViewById(R.id.mRadio_cinema);
        mbutton1 = findViewById(R.id.mbutton1);
        mbutton2 = findViewById(R.id.mbutton2);
        mrecyclerbuy = findViewById(R.id.mrecyclerBuy);
        mreturn = findViewById(R.id.mreturn);
        //设置适配器
        buyTicketAdapter = new BuyTicketAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrecyclerbuy.setLayoutManager(layoutManager);
        mrecyclerbuy.setAdapter(buyTicketAdapter);
        //默认选中第一个
        mradio_cinema.check(mradio_cinema.getChildAt(0).getId());
        mbutton1.setTextColor(Color.WHITE);
        //获取数据
        ticketPresenter = new BuyTicketPresenter(new BuyTicket());
        ticketPresenter.request(userId,sessionId,1,10,status);
        //按钮组
        mradio_cinema.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mbutton1:
                        buyTicketAdapter.clear();
                        mbutton1.setTextColor(Color.WHITE);
                        mbutton2.setTextColor(Color.BLACK);
                        status = 1;
                        ticketPresenter = new BuyTicketPresenter(new BuyTicket());
                        ticketPresenter.request(userId,sessionId,1,10,status);
                        break;
                    case R.id.mbutton2:
                        buyTicketAdapter.clear();
                        mbutton1.setTextColor(Color.BLACK);
                        mbutton2.setTextColor(Color.WHITE);
                        status = 2;
                        ticketPresenter = new BuyTicketPresenter(new BuyTicket());
                        ticketPresenter.request(userId,sessionId,1,10,status);
                        break;
                }
            }
        });
        buyTicketAdapter.setSingleBack(this);
    }

    @Override
    public void back(int pos, final int uid, final int count, final String hao) {
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
                        Toast.makeText(BuyTicketActivity.this, "微信", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wx_but2:
                        Toast.makeText(BuyTicketActivity.this, "支付宝", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        dialog.findViewById(R.id.wx_qd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WxPresenter moviekeyPresenter = new WxPresenter(new WxData());
                moviekeyPresenter.request(userId,sessionId,1,hao);
            }
        });
    }
    class WxData implements DataCall<Result> {
        @Override
        public void success(Result data) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(BuyTicketActivity.this, null);
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
            finish();
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    class BuyTicket implements DataCall<Result<List<BuyTicketList>>>{
        @Override
        public void success(Result<List<BuyTicketList>> data) {
            if (data.getStatus().equals("0000")){
                List<BuyTicketList> result = data.getResult();
                buyTicketAdapter.addAll(result);
                buyTicketAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //点击返回
    @OnClick(R.id.mreturn)
    public void mreturn(){
        finish();
    }
}
