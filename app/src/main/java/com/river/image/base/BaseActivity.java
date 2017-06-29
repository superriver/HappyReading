package com.river.image.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.github.mzule.fantasyslide.SideBar;
import com.github.mzule.fantasyslide.SimpleFantasyListener;
import com.jakewharton.rxbinding.view.RxView;
import com.river.image.R;
import com.river.image.annotation.ActivityFragmentInject;
import com.river.image.module.joke.JokeActivity;
import com.river.image.module.news.ui.NewsActivity;
import com.river.image.module.picture.view.home.HomeActivity;
import com.river.image.utils.ActivityManager;
import com.river.image.utils.NetUtil;
import com.socks.library.KLog;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
    implements BaseView {

  protected Unbinder mUnbinder;
  private DrawerLayout mDrawerLayout;

  //fragment布局Id
  protected abstract int getFragmentContentId();

  protected abstract void initView();

  protected int contentViewId;
  protected int mToolbarTitle;
  protected boolean hasNavigationView;
  /**
   * 将代理类通用行为抽出来
   */
  protected T mPresenter;
  // 跳转的类
  protected Class mClass;
boolean isConnected;
  protected Button mButton,mButtonShow;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //指定的注解类是否存在
    if (NetUtil.isConnected(this)) {
      if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
        ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
        contentViewId = annotation.contentViewId();
        mToolbarTitle = annotation.toolbarTitle();
        hasNavigationView = annotation.hasNavigationView();
        KLog.d("BaseActivity", "isConnected");
        setContentView(contentViewId);
        mUnbinder = ButterKnife.bind(this);
        // ActivityManager.getInstance().addActivity(this);
        //避免重复添加Fragment
        initView();
        initToolBar();
        if (hasNavigationView) {
          initDrawLayout();
        }
      }
    }else {
      setContentView(R.layout.network_failed);
      mButton= (Button) findViewById(R.id.btn_open_net);
     // mButtonShow= (Button) findViewById(R.id.btn_show);
      mButton.setVisibility(View.VISIBLE);
      mButtonShow.setVisibility(View.GONE);
      RxView.clicks(mButton).subscribe(new Action1<Void>() {
        @Override public void call(Void aVoid) {
          NetUtil.openSetting(BaseActivity.this);
        }
      });
    }

  }

  @Override protected void onRestart() {
    super.onRestart();
    //if(NetUtil.isConnected(BaseActivity.this)){
    //  mButton.setVisibility(View.GONE);
    //  mButtonShow.setVisibility(View.VISIBLE);
    //  RxView.clicks(mButtonShow).subscribe(new Action1<Void>() {
    //    @Override public void call(Void aVoid) {
    //      finish();
    //    }
    //  });
    //}else {
    //
    //}

  }
  @Override protected void onResume() {
    super.onResume();
    KLog.d("BaseActivity","onResume");

  }

  //添加fragment
  protected void addFragment(BaseFragment fragment) {
    if (fragment != null) {
      getSupportFragmentManager().beginTransaction()
          .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
          .addToBackStack(fragment.getClass().getSimpleName())
          .commitAllowingStateLoss();
    }
  }

  public void initToolBar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      toolbar.setContentInsetStartWithNavigation(0);
      setSupportActionBar(toolbar);
      if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      }
      if (mToolbarTitle != -1) {
        getSupportActionBar().setTitle(mToolbarTitle);
      }
    }
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
  }

  public void initDrawLayout() {
    final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
    indicator.setColor(Color.WHITE);
    getSupportActionBar().setHomeAsUpIndicator(indicator);

    mDrawerLayout.setScrimColor(Color.TRANSPARENT);

    SideBar leftSideBar = (SideBar) findViewById(R.id.leftSideBar);
    leftSideBar.setSelected(true);
    leftSideBar.setFantasyListener(new SimpleFantasyListener() {
      @Override public boolean onSelect(View view) {
        int index = view.getId();
        switch (index) {
          case R.id.action_news:
            mClass = NewsActivity.class;
            break;
          case R.id.action_photo:
            mClass = HomeActivity.class;
            break;
          case R.id.action_jokes:
            mClass = JokeActivity.class;
            break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
      }
    });

    mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
      @Override public void onDrawerSlide(View drawerView, float slideOffset) {
        if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
          indicator.setProgress(slideOffset);
        }
      }

      @Override public void onDrawerClosed(View drawerView) {
        if (mClass != null) {
          showActivityReorderToFront(BaseActivity.this, mClass, false);
          mClass = null;
        }
      }
    });
  }

  private void showActivityReorderToFront(Activity activity, Class aClass, boolean b) {
    Toast.makeText(activity, "" + aClass.getSimpleName(), Toast.LENGTH_SHORT).show();
    Intent intent = new Intent();
    intent.setClass(activity, aClass);
    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    activity.startActivity(intent);
    overridePendingTransition(0, 0);
  }

  public void onClick(View view) {
    if (view instanceof TextView) {
      String title = ((TextView) view).getText().toString();
      if (title.startsWith("星期")) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
      } else {
        //startActivity(UniversalActivity.newIntent(this, title));
      }
    } else if (view.getId() == R.id.userInfo) {
      // startActivity(UniversalActivity.newIntent(this, "个人中心"));
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
      } else {
        mDrawerLayout.openDrawer(GravityCompat.START);
      }
    }
    return true;
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    KLog.d("onKeyDown","onKeyDown");
    if (event.getAction() == keyCode) {
      if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
        finish();
        return true;
      }
    }
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override public void onBackPressed() {

    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
      super.onBackPressed();
      KLog.d("TAG","onBackPressed");
    }else {
      KLog.d("TAG","finish");
      finish();
      overridePendingTransition(0,0);
    }

  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ActivityManager.getInstance().finishActivity(this);
    if(null!=mUnbinder){
      mUnbinder.unbind();
    }
  }
}