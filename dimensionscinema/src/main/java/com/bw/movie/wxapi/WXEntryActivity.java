package com.bw.movie.wxapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.WxLoginPresenter;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wxlogin);
        sp = getSharedPreferences("xian",Context.MODE_PRIVATE);
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
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.e("flag", "-----code:ok");
                if (baseResp instanceof SendAuth.Resp) {
                    SendAuth.Resp sendAuthResp = (SendAuth.Resp) baseResp;
                    //Toast.makeText(this, sendAuthResp.code+"", Toast.LENGTH_SHORT).show();
                    WxLoginPresenter wxLoginPresenter = new WxLoginPresenter(new WxLogin());
                    wxLoginPresenter.request(sendAuthResp.code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (baseResp instanceof SendAuth.Resp) {}
                Log.e("flag", "-----授权取消:");
                Toast.makeText(this, "授权取消:", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (baseResp instanceof SendAuth.Resp) {}
                Log.e("flag", "-----授权失败:");
                Toast.makeText(this, "授权失败:", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }

    private class WxLogin implements DataCall<Result<User>> {
        @Override
        public void success(Result<User> data) {
            Toast.makeText(WXEntryActivity.this, data.getResult().getUserInfo().toString()+"", Toast.LENGTH_SHORT).show();
            Log.e("LL",data.getResult().getUserInfo().toString());
            Log.e("LL",data.getResult().getUserId()+"----"+data.getResult().getSessionId());
            if(data.getStatus().equals("0000")){
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("xian",true);
                edit.commit();
                //添加数据库
                User result = data.getResult();
                AllUser allUser = new AllUser(1,result.getSessionId(),result.getUserId(),result.getUserInfo().getNickName(),result.getUserInfo().getPhone(),result.getUserInfo().getBirthday(),result.getUserInfo().getSex(),result.getUserInfo().getLastLoginTime(),result.getUserInfo().getHeadPic());
                AllUserDao allUserDao = MyApp.daoSession.getAllUserDao();
                allUserDao.insertOrReplace(allUser);
                finish();
            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
}

