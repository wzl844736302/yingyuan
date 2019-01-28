package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    private TextView payResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        payResult = findViewById(R.id.pay_result);
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
                    //支付成功后的逻辑
                    Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    Toast.makeText(this, "微信支付失败", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    Toast.makeText(this, "微信支付取消", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(this, "微信支付未知异常", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
            payResult.setText(result);
        }
    }

}
