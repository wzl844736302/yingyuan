package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.view.BuyTicketActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    private TextView payResult;
    private ImageView success,fail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        //绑定
        ButterKnife.bind(this);
        //初始化控件
        payResult = findViewById(R.id.pay_result);
        success = findViewById(R.id.iv_success);
        fail = findViewById(R.id.iv_fail);
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    success.setVisibility(View.VISIBLE);
                    fail.setVisibility(View.GONE);
                    //支付成功后的逻辑
                    Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    success.setVisibility(View.GONE);
                    fail.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "微信支付失败", Toast.LENGTH_SHORT).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    success.setVisibility(View.GONE);
                    fail.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "微信支付取消", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "微信支付未知异常", Toast.LENGTH_SHORT).show();
                    break;
            }
            payResult.setText(result);
        }
    }
    //点击返回
    @OnClick(R.id.back)
    public void back(){
        //跳转
        finish();
    }
    //点击查看订单
    @OnClick(R.id.chakan)
    public void chakan(){
        //跳转
        Intent intent = new Intent(WXPayEntryActivity.this, BuyTicketActivity.class);
        startActivity(intent);
        finish();
    }
}
