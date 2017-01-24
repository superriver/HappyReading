package com.river.image.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/1/5.
 */

public class NetUtil {
  private NetUtil() {
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  /**
   * 判断网络是否连接
   */
  public static boolean isConnected(Context context) {
    ConnectivityManager connectivity =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (null != info && info.isConnected()) {
        if (info.getState() == NetworkInfo.State.CONNECTED) return true;
      }
    }
    return false;
  }

  /**
   * 判断是否wifi连接
   */
  public static boolean isWifi(Context context) {
    ConnectivityManager connectivity =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity == null) return false;
    return connectivity.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
  }
  /**
   * 打开网络设置界面
   */
  public static void openSetting(Activity activity){
    Intent intent = new Intent("/");
    ComponentName componentName = new ComponentName("com.android.setting","com.android.setting.WirelessSettings");
    intent.setComponent(componentName);
    intent.setAction("android.intent.action.VIEW");
    activity.startActivityForResult(intent,0);
  }

}
