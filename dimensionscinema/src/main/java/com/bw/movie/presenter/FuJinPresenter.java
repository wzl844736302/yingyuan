package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.Irequest;
import com.bw.movie.utils.NetWorkUtils;

import io.reactivex.Observable;

public class FuJinPresenter extends BasePresenter{
    public FuJinPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        Irequest irequest = NetWorkUtils.getMinstance().create(Irequest.class);
        return irequest.findNearbyCinemas((int)args[0],(String) args[1],(String)args[2],(String)args[3],(int)args[4],(int)args[5]);
    }
}
