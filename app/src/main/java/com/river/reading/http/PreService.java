package com.river.reading.http;

import com.river.reading.bean.GirlsBean;
import com.river.reading.bean.NewsBean;
import com.river.reading.bean.NewsChannel;
import com.river.reading.bean.JokeBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface PreService {
  /**
   * 图片请求API
   */
  @GET("api/data/{type}/{count}/{page}") Observable<GirlsBean> getImageInfo(
      @Path("type") String type, @Path("count") int count, @Path("page") int page);

  /**
   * 新闻频道请求api
   */
  @GET("109-34") Observable<NewsChannel> getNewsChannel(@Query("showapi_appid") String appId,
      @Query("showapi_timestamp") String timestamp, @Query("showapi_sign") String sign);

  /**
   * 新闻列表
   */
  @GET("109-35") Observable<NewsBean> getNewsList(@Query("channelId") String channelId,
      @Query("channelName") String channelName, @Query("maxResult") String maxResult,
      @Query("needAllList") String needAllList, @Query("needContent") String needContent,
      @Query("needHtml") String needHtml, @Query("page") String page,
      @Query("showapi_appid") String appid, @Query("showapi_timestamp") String timestamp,
      @Query("title") String title, @Query("showapi_sign") String showapi_sign);

  /**
   * 文本笑话大全API
   */
  @GET("{type}") Observable<JokeBean> getTextJokeList(@Path("type") String type,
      @Query("maxResult") String maxResult,
      @Query("page") String page,
      @Query("showapi_appid") String appid,
      @Query("showapi_timestamp") String timestamp,
      @Query("showapi_sign") String showapi_sign);
}
