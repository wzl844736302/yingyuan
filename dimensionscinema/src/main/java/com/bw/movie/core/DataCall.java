package com.bw.movie.core;


import com.bw.movie.core.exception.ApiException;

public interface DataCall<T> {

    void success(T data);
    void fail(ApiException e);

}
