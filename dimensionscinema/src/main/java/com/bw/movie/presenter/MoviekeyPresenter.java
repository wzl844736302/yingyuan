package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.Irequest;
import com.bw.movie.utils.NetWorkUtils;

import io.reactivex.Observable;

public class MoviekeyPresenter extends BasePresenter{

    public MoviekeyPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        Irequest irequest = NetWorkUtils.getMinstance().create(Irequest.class);
        return irequest.buyMovieTicket((int)args[0],(String)args[1],(int) args[2],(int)args[3],(String)args[4]);
    }
}
