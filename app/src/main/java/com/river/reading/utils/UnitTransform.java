package com.river.reading.utils;

import android.content.Context;

/**
 * Created by Administrator on 2016/11/11.
 */

public class UnitTransform {
  /**
   * dpתpx
   *
   */
  public static int dip2px(Context ctx,float dpValue) {
    final float scale = ctx.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }


  /**
   *	pxתdp
   */
  public static int px2dip(Context ctx,float pxValue) {
    final float scale = ctx.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }
}
