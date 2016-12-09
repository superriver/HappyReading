package com.river.image.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Window;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.river.image.R;
import com.river.image.module.news.ui.OnTabSelectedListenerAdapter;
import com.river.image.utils.ActivityManager;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{

  protected Unbinder mUnbinder;
  //布局文件Id
  protected  abstract int getContentViewId();
  //fragment布局Id
  protected  abstract int getFragmentContentId();
  protected  abstract void initView();

  /**
   * 将代理类通用行为抽出来
   */
  protected T mPresenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(getContentViewId());
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
    if(event.getAction()==keyCode){
        if(getSupportFragmentManager().getBackStackEntryCount()==1)
        {
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

  protected void setOnTabSelectEvent(final ViewPager viewPager, final TabLayout tabLayout) {
    tabLayout.addOnTabSelectedListener(new OnTabSelectedListenerAdapter() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition(), true);
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
       // RxBus.get().post("enableRefreshLayoutOrScrollRecyclerView", tab.getPosition());
      }
    });
  }
}
