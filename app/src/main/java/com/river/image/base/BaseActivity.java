package com.river.image.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.river.image.annotation.ActivityFragmentInject;
import com.river.image.utils.ActivityManager;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
    implements BaseView {

  protected Unbinder mUnbinder;

  //fragment布局Id
  protected abstract int getFragmentContentId();

  protected abstract void initView();

  protected int contentViewId;
  /**
   * 将代理类通用行为抽出来
   */
  protected T mPresenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // requestWindowFeature(Window.FEATURE_NO_TITLE);
    //指定的注解类是否存在
    if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
      ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
      contentViewId = annotation.contentViewId();
    }
    setContentView(contentViewId);
    mUnbinder = ButterKnife.bind(this);
    // ActivityManager.getInstance().addActivity(this);
    //避免重复添加Fragment

    initView();
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

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getAction() == keyCode) {
      if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
        finish();
        return true;
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ActivityManager.getInstance().finishActivity(this);
    mUnbinder.unbind();
  }
}