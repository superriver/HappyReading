package com.river.image.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import com.river.image.common.MyApplication;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/9/26.
 */

public class BitmapUtil {
  public static Bitmap drawableToBitmap(Drawable drawable){

    int width = drawable.getIntrinsicWidth();
    int height = drawable.getIntrinsicHeight();
    Bitmap mBitmap = Bitmap.createBitmap(width,height,
        drawable.getOpacity()!= PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
    Canvas canvas = new Canvas(mBitmap);
    drawable.setBounds(0,0,width,height);
    drawable.draw(canvas);
    return mBitmap;
  }
  public static boolean saveBitmap(Bitmap bitmap,String dir,String name,boolean isShowPhotos){
    File path = new File(dir);
    if(!path.exists()){
      path.mkdirs();
    }
    File file = new File(path+File.separator+name);
    if(file.exists()){
      file.delete();
    }else {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if(isShowPhotos){
      MediaStore.Images.Media.insertImage(MyApplication.getInstance().getContentResolver(),bitmap,name,null);
      MyApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+file)));
    }
    return true;
  }
}
