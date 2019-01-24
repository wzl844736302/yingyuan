package com.bw.movie.core;

import com.bw.movie.bean.HotMovie;
import com.bw.movie.bean.MovieDetail;
import com.bw.movie.bean.Recommend;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Irequest {
    /**
     * 登录
     *
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
     *
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

    /**
     * 热门电影
     * @param userId
     * @param sessionId
     * @param
     * @param
     * @return
     */
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<HotMovie>>> hotMovie(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);

    /**
     * 正在上映
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<HotMovie>>> findReleaseMovieList(@Header("userId") int userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Query("page") int page,
                                                            @Query("count") int count);

    /**
     * 即将上映
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<HotMovie>>> findComingSoonMovieList(@Header("userId") int userId,
                                                               @Header("sessionId") String sessionId,
                                                               @Query("page") int page,
                                                               @Query("count") int count);

    /**
     * 推荐影院
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<Recommend>>> findRecommendCinemas(@Header("userId") int userId,
                                                             @Header("sessionId") String sessionId,
                                                             @Query("page") int page,
                                                             @Query("count") int count);
    /**
     * 附近影院
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<Recommend>>> findNearbyCinemas(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("longitude")String longitude,
                                                          @Query("latitude")String latitude,
                                                          @Query("page") int page,
                                                          @Query("count") int count);

    @GET("movie/v1/findMoviesDetail")
    Observable<Result<MovieDetail>> findDetailMovie(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("movieId")int movieId);
}
