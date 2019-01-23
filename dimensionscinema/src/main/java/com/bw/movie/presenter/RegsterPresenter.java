package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.Irequest;
import com.bw.movie.utils.NetWorkUtils;

import io.reactivex.Observable;

public class RegsterPresenter extends BasePresenter{

    public RegsterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        Irequest irequest = NetWorkUtils.getMinstance().create(Irequest.class);
        return irequest.register((String)args[0],(String)args[1],(String)args[2],(String)args[3],(int)args[4],(String)args[5],(String)args[6],
                (String)args[7], (String)args[8],(String)args[9],(String)args[10]);
    }
}
