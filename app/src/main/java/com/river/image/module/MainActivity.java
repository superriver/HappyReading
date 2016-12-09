//package com.river.image.module;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import com.river.image.R;
//import com.river.image.base.BaseActivity;
//import com.river.image.base.BaseFragment;
//import com.river.image.module.news.ui.NewsFragment;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/11/10.
// */
//
//public class MainActivity extends BaseActivity {
//  private Map<String, BaseFragment> mFragmentMap;
//  private String mLastFragmentName;
//  @Override protected void initView() {
//    mFragmentMap = new HashMap<>();
//    mFragmentMap.put("新闻",new NewsFragment());
//    switch2Fragment("新闻");
//  }
//  public void switch2Fragment(CharSequence title){
//    BaseFragment showFragment=mFragmentMap.get(title);
//    if(null==showFragment){
//      return;
//    }
//    String showTag = showFragment.toString();
//    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//    if(null==getSupportFragmentManager().findFragmentByTag(mLastFragmentName)){
//      fragmentTransaction.hide(showFragment);
//    }
//    Fragment hideFragment = getSupportFragmentManager().findFragmentByTag(showTag);
//
//    if(null==hideFragment){
//      if (fragmentTransaction.isEmpty()) {
//
//        fragmentTransaction.replace(R.id.frame_content, showFragment, showTag);
//      } else if (!showFragment.isAdded()) {
//
//        fragmentTransaction.add(R.id.frame_content, showFragment, showTag);
//      }
//    }
//    fragmentTransaction.show(showFragment);
//    //commit
//    fragmentTransaction.commit();
//    mLastFragmentName = showTag;
//
//  }
//  @Override protected int getContentViewId() {
//    return R.layout.activity_main;
//  }
//
//  @Override protected int getFragmentContentId() {
//    return 0;
//  }
//
//
//}
