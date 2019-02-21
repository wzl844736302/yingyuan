package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.Irequest;
import com.bw.movie.utils.NetWorkUtils;

import io.reactivex.Observable;

public class CommentPresenter extends BasePresenter{

    public CommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        Irequest irequest = NetWorkUtils.getMinstance().create(Irequest.class);
        return irequest.findAllMovieComment((int)args[0],(String)args[1],(int)args[2],1,100);
    }
}
