package com.river.image.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsBean{
  @JsonProperty("showapi_res_code")
  public int showapi_res_code;
  @JsonProperty("showapi_res_error")
  public String showapi_res_error;
  @JsonProperty("showapi_res_body")
  public ShowapiResBodyBean showapi_res_body;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ShowapiResBodyBean {
    @JsonProperty("ret_code")
    public int ret_code;
    @JsonProperty("pagebean")
    public PageBean pagebean;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageBean {
      @JsonProperty("allPages")
      public int allPages;
      @JsonProperty("currentPage")
      public int currentPage;
      @JsonProperty("allNum")
      public int allNum;
      @JsonProperty("maxResult")
      public int maxResult;
      @JsonProperty("contentlist")
      public List<ContentBean> contentlist;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ContentBean implements Serializable{
          @JsonProperty("content")
          public String content;
          @JsonProperty("pubDate")
          public String pubDate;
          @JsonProperty("havePic")
          public boolean havePic;
          @JsonProperty("title")
          public String title;
          @JsonProperty("channelName")
          public String channelName;
          @JsonProperty("desc")
          public String desc;
          @JsonProperty("source")
          public String source;
          @JsonProperty("channelId")
          public String channelId;
         // @JsonProperty("nid")
         // public String nid;
          @JsonProperty("link")
          public String link;
         // @JsonProperty("allList")
        //  public List<String> allList;
          @JsonProperty("imageurls")
          public List<ImageURL> imageurls;


          @JsonIgnoreProperties(ignoreUnknown = true)
          public static class ImageURL implements Serializable{
            @JsonProperty("height")
            public int height;
            @JsonProperty("width")
            public  int width;
            @JsonProperty("url")
            public String url;
          }

      }
    }
  }

}
