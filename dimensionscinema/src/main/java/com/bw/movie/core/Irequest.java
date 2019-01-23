package com.bw.movie.core;

import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
import com.bw.movie.bean.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Irequest {
    /**
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<Result<User>> login(@Field("phone") String mobile,
                                     @Field("pwd") String password);
}
