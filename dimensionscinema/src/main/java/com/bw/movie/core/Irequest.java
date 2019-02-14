package com.bw.movie.core;

import com.bw.movie.bean.BuyTicketList;
import com.bw.movie.bean.CinemaInfo;
import com.bw.movie.bean.CinemasList;
import com.bw.movie.bean.Comment;
import com.bw.movie.bean.FocusList;
import com.bw.movie.bean.HotMovie;
import com.bw.movie.bean.MessageList;
import com.bw.movie.bean.ModifyUser;
import com.bw.movie.bean.MovieDetail;
import com.bw.movie.bean.MovieList;
import com.bw.movie.bean.QureyUser;
import com.bw.movie.bean.Recommend;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.ScheduleList;
import com.bw.movie.bean.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    /**
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<MovieDetail>> findDetailMovie(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("movieId")int movieId);

    /**
     * 查询用户信息
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("user/v1/verify/getUserInfoByUserId")
    Observable<Result<QureyUser>> getUserInfoByUserId(@Header("userId") int userId,
                                                      @Header("sessionId") String sessionId);

    /**
     * 根据影院ID查询该影院当前排期的电影列表
     * @param cinemaId
     * @return
     */
    @GET("movie/v1/findMovieListByCinemaId")
    Observable<Result<List<MovieList>>> findMovieListByCinemaId(@Query("cinemaId")int cinemaId);

    /**
     * 电影详情列表
     * @param userId
     * @param sessionId
     * @param movieId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<Comment>>> findAllMovieComment(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("movieId") int movieId,
                                                          @Query("page") int page,
                                                          @Query("count") int count);
    /**
     * 根据电影ID和影院ID查询电影排期列表
     * @param cinemasId
     * @param movieId
     * @return
     */
    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<ScheduleList>>> findMovieScheduleList(@Query("cinemasId")int cinemasId,
                                                                 @Query("movieId")int movieId);

    /**
     * 根据电影ID查询当前排片该电影的影院列表
     * @param movieid
     * @return
     */
    @GET("movie/v1/findCinemasListByMovieId")
    Observable<Result<List<CinemasList>>> findCinemasListByMovieId(@Query("movieId")int movieid);

    /**
     * 关注影院
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("cinema/v1/verify/followCinema")
    Observable<Result> followCinema(@Header("userId")int userId,
                                   @Header("sessionId")String sessionId,
                                   @Query("cinemaId")int movieId);

    /**
     * 取消关注影院
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinema(@Header("userId")int userId,
                                          @Header("sessionId")String sessionId,
                                          @Query("cinemaId")int movieId);

    /**
     * 查询用户关注的影院信息
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<FocusList>>> findCinemaPageList(@Header("userId")int userId,
                                                           @Header("sessionId")String sessionId,
                                                           @Query("page")int page,
                                                           @Query("count")int count);

    /**
     * 关注电影
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/verify/followMovie")
    Observable<Result> followMovie(@Header("userId")int userId,
                                   @Header("sessionId")String sessionId,
                                   @Query("movieId")int movieId);

    /**
     * 取消关注电影
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId")int userId,
                                         @Header("sessionId")String sessionId,
                                         @Query("movieId")int movieId);

    /**
     * 查询用户关注的影片列表
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/verify/findMoviePageList")
    Observable<Result<List<MovieList>>> findMoviePageList(@Header("userId")int userId,
                                         @Header("sessionId")String sessionId,
                                         @Query("page")int page,
                                         @Query("count")int count);

    /**
     * 下单
     * @param userId
     * @param sessionId
     * @param scheduleId
     * @param amount
     * @param sign
     * @return
     */
    @POST("movie/v1/verify/buyMovieTicket")
    @FormUrlEncoded
    Observable<Result> buyMovieTicket(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Field("scheduleId") int scheduleId,
                                                   @Field("amount") int amount,
                                                   @Field("sign") String sign);

    /**
     * 微信支付
     * @param userId
     * @param sessionId
     * @param payType
     * @param orderId
     * @return
     */
    @POST("movie/v1/verify/pay")
    @FormUrlEncoded
    Observable<Result> pay(@Header("userId") int userId,
                           @Header("sessionId") String sessionId,
                           @Field("payType") int payType,
                           @Field("orderId") String orderId);

    /**
     * 用户购票记录查询列表
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @param status
     * @return
     */
    @GET("user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<BuyTicketList>>> findUserBuyTicketRecordList(@Header("userId") int userId,
                                                                        @Header("sessionId") String sessionId,
                                                                        @Query("page")int page,
                                                                        @Query("count")int count,
                                                                        @Query("status")int status);

    @POST("user/v1/weChatBindingLogin")
    @FormUrlEncoded
    Observable<Result<User>> wxlogin(@Field("code") String code);




    @GET("cinema/v1/findCinemaInfo")
    Observable<Result<CinemaInfo>> findCinemaInfo(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Query("cinemaId")int cinemaId);

    /**
     * 查询系统消息列表
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<MessageList>>> findAllSysMsgList(@Header("userId") int userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Query("page")int page,
                                                            @Query("count")int count);
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<Comment>>> findMovieComment(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("movieId") int movieId,
                                                          @Query("page") int page,
                                                          @Query("count") int count);

    /**
     * 上传头像
     * @param userId
     * @param sessionId
     * @param body
     * @return
     */
    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result> uploadHeadPic(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Body MultipartBody body);

    /**
     * 修改用户信息
     * @param userId
     * @param sessionId
     * @param sex
     * @param email
     * @return
     */
    @POST("user/v1/verify/modifyUserInfo")
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<Result> modifyUserInfo(@Header("userId")int userId,
                                                  @Header("sessionId")String sessionId,
                                                  @Field("nickName")String nickName,
                                                  @Field("sex")int sex,
                                                  @Field("email")String email);

    /**
     * 添加用户对影片的评论
     * @param userid
     * @param sessionId
     * @param movieId
     * @param comment
     * @return
     */
    @POST("movie/v1/verify/movieComment")
    @FormUrlEncoded
    Observable<Result> movieComment(@Header("userId")int userid,
                                    @Header("sessionId")String sessionId,
                                    @Field("movieId")int movieId,
                                    @Field("commentContent")String comment);

    /**
     * 添加用户对评论的回复
     * @param userid
     * @param sessionId
     * @param comment
     * @param reply
     * @return
     */
    @POST("movie/v1/verify/commentReply")
    @FormUrlEncoded
    Observable<Result> commentReply(@Header("userId")int userid,
                                    @Header("sessionId")String sessionId,
                                    @Field("commentId")int comment,
                                    @Field("replyContent")String reply);

    /**
     * 意见反馈
     * @param userid
     * @param sessionId
     * @param content
     * @return
     */
    @POST("tool/v1/verify/recordFeedBack")
    @FormUrlEncoded
    Observable<Result> recordFeedBack(@Header("userId")int userid,
                                      @Header("sessionId")String sessionId,
                                      @Field("content") String content);

    /**
     * 影院评论点赞
     * @param userid
     * @param sessionId
     * @param commentId
     * @return
     */
    @POST("cinema/v1/verify/cinemaCommentGreat")
    @FormUrlEncoded
    Observable<Result> cinemaCommentGreat(@Header("userId")int userid,
                                          @Header("sessionId")String sessionId,
                                          @Field("commentId")int commentId);
    @POST("user/v1/verify/modifyUserPwd")
    @FormUrlEncoded
    Observable<Result> modifyUserPwd(@Header("userId") int userId,
                           @Header("sessionId") String sessionId,
                           @Field("oldPwd") String oldPwd,
                           @Field("newPwd") String newPwd,
                           @Field("newPwd2") String newPwd2);
    @POST("movie/v1/verify/movieComment")

    @FormUrlEncoded
    Observable<Result> Comment1(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Field("movieId") int movieId,
                                     @Field("commentContent") String commentContent);

    /**
     * 用户签到
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId);
}
