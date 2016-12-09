package com.river.image.http;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ApiConfig {
  public static final String TEXT_JOKE="341-1";
  public static final String IMG_JOKE="341-2";
  public static final String DYNAMIC_JOKE="341-3";
  //干货集中营
  public static final String GANHUO_API = "http://gank.io/";
  //新闻
   public static final String NEWS_URL = "https://route.showapi.com/";
  public static final String  SHOWAPI_APPID = "13642";
  public static final String  SHOWAPI_TIMESTAP = "20161018161339";
  public static final String  SHOWAPI_SIGN = "fb6b356d2fb740e38d84b4515f08c51e";
  //视频
  public static final String VIDEO_API="";
  //笑话
  public static final String JOKE_API="https://route.showapi.com/";

  //根据host
  public static String getHost(int hostType){
    switch (hostType) {
      case 0:
        return ApiConfig.NEWS_URL;
      case 1:
        return ApiConfig.GANHUO_API;
      case 2:
        //return ApiConfig.NEWS_URL+
    }
    return "";
  }
}
