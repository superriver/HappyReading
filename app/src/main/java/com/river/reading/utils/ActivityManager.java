package com.river.reading.utils;

import android.app.Activity;
import android.content.Context;
import java.util.Stack;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ActivityManager {
  public static ActivityManager manager;
  public static Stack<Activity> activityStack;
  static {
    manager = new ActivityManager();
  }
  public static ActivityManager getInstance(){
      return  manager;
    }
  //添加栈
  public void addActivity(Activity activity){
    if(activityStack==null){
      activityStack = new Stack<>();
    }
    activityStack.add(activity);
  }
  //获取当前的Activity
  public Activity getCurrentActivity(){
  Activity activity =  activityStack.lastElement();
    return  activity;
  }
  //结束当前的Activity
  public void finishAcitivity(){
    Activity activity = getCurrentActivity();
    finishActivity(activity);
  }
  //结束指定的Activity
  public void finishActivity(Activity activity){
    if(activityStack!=null) {
      activityStack.pop();
      activity.finish();
    }
  }
  //结束全部的Aictivity
  public void finishAllActivity(){
    for (int i = 0,size=activityStack.size(); i <size ; i++) {
      if(null!=activityStack.get(i)) {
        activityStack.get(i).finish();
      }
    }
    activityStack.clear();
  }
  //退出应用程序
  public void AppExit(Context context){
    android.app.ActivityManager activityManager =
        (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    activityManager.killBackgroundProcesses(context.getPackageName());
    System.exit(0);
  }
}
