package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.Irequest;
import com.bw.movie.utils.NetWorkUtils;

import io.reactivex.Observable;


public class ModifyUserPresenter extends BasePresenter{
    public ModifyUserPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        Irequest irequest = NetWorkUtils.getMinstance().create(Irequest.class);
        return irequest.modifyUserInfo((int)args[0],(String) args[1],(String) args[2],(int)args[3],(String) args[4]);
    }
}

