package com.river.image.utils;

import android.content.Context;
import android.os.Environment;
import com.socks.library.KLog;

/**
 * Created by Administrator on 2016/9/27.
 */

public class FileUtil {
  public static String getDiskCatchDir(Context context){
    String cachePath;
    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())||!Environment.isExternalStorageEmulated()){
      KLog.a("TAG", context);
      cachePath = context.getExternalCacheDir().getAbsolutePath();

    }else {
     cachePath = context.getCacheDir().getAbsolutePath();
    }
    return  cachePath;
  }

}
