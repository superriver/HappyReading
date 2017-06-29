package com.river.image.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

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
    if (context != null) {

      ConnectivityManager mConnectivityManager = (ConnectivityManager) context
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
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
    Intent intent=null;
    //判断手机系统的版本  即API大于10 就是3.0或以上版本
    if(android.os.Build.VERSION.SDK_INT>10){
      intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
    }else{
      intent = new Intent();
      ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
      intent.setComponent(component);
      intent.setAction("android.intent.action.VIEW");
    }
    activity.startActivity(intent);
  }

  /**
   * 判断WIFI网络是否可用
   * @param context
   * @return
   */
  public static boolean isWifiConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager = (ConnectivityManager) context
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mWiFiNetworkInfo = mConnectivityManager
          .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      if (mWiFiNetworkInfo != null) {
        return mWiFiNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  /**
   * 判断MOBILE网络是否可用
   * @param context
   * @return
   */
  public static boolean isMobileConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager = (ConnectivityManager) context
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mMobileNetworkInfo = mConnectivityManager
          .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      if (mMobileNetworkInfo != null) {
        return mMobileNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  /**
   * 获取当前网络连接的类型信息
   * @param context
   * @return
   */
  public static int getConnectedType(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager = (ConnectivityManager) context
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
        return mNetworkInfo.getType();
      }
    }
    return -1;
  }


  /**
   * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
   *
   * @param context
   * @return
   */
  public static int getAPNType(Context context) {
    int netType = 0;
    ConnectivityManager connMgr = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo == null) {
      return netType;
    }
    int nType = networkInfo.getType();
    if (nType == ConnectivityManager.TYPE_WIFI) {
      netType = 1;// wifi
    } else if (nType == ConnectivityManager.TYPE_MOBILE) {
      int nSubType = networkInfo.getSubtype();
      TelephonyManager mTelephony = (TelephonyManager) context
          .getSystemService(Context.TELEPHONY_SERVICE);
      if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
          && !mTelephony.isNetworkRoaming()) {
        netType = 2;// 3G
      } else {
        netType = 3;// 2G
      }
    }
    return netType;
  }


}
