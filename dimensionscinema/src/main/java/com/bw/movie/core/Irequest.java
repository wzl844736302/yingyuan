package com.bw.movie.core;

import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;
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

    /**
     * 注册
     * @param nickName
     * @param mobile
     * @param pwd
     * @param pwd2
     * @param sex
     * @param birthday
     * @param imei
     * @param ua
     * @param screenSize
     * @param os
     * @param email
     * @return
     */
    @POST("user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> register(@Field("nickName") String nickName,
                                      @Field("phone") String mobile,
                                      @Field("pwd") String pwd,
                                      @Field("pwd2") String pwd2,
                                      @Field("sex") int sex,
                                      @Field("birthday") String birthday,
                                      @Field("imei") String imei,
                                      @Field("ua") String ua,
                                      @Field("screenSize") String screenSize,
                                      @Field("os") String os,
                                      @Field("email") String email);
}
