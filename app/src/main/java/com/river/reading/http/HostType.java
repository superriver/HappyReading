package com.river.reading.http;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/10/14.
 */

public class HostType {
  /**
   * 多少种Host类型
   */

  public static final int TYPE_COUNT = 2;
  /**
   * 新闻和笑话host
   */
  @HostTypeChecker
  public static final int NEWS_HOST=0;
  /**
   * 图片host
   */
  @HostTypeChecker
  public static final int IMAGE_HOST=1;

  @IntDef({NEWS_HOST,IMAGE_HOST})
  @Retention(RetentionPolicy.SOURCE)
  public @interface HostTypeChecker{

  }

}
