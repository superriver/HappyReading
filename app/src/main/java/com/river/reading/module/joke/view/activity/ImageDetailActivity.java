package com.river.reading.module.joke.view.activity;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import com.river.image.R;
import com.river.reading.annotation.ActivityFragmentInject;
import com.river.reading.base.BaseActivity;
import com.river.reading.base.BaseFragment;
import com.river.reading.event.MessageEvent;
import com.river.reading.module.joke.view.fragment.ImageDetailFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/9/22.
 */
@ActivityFragmentInject(contentViewId = R.layout.image_girl_detail) public class ImageDetailActivity
    extends BaseActivity {
  ImageDetailFragment mGirlFragment;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  @Override protected int getFragmentContentId() {
    return R.id.girl_fragment;
  }

  @Override protected void initView() {
    EventBus.getDefault().register(ImageDetailActivity.this);
    mToolbar.setTitle("福利");
    setSupportActionBar(mToolbar);
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onMessageEvent(MessageEvent event) {
    mGirlFragment = new ImageDetailFragment(event);
    if (null == getSupportFragmentManager().getFragments()) {
      BaseFragment firstFragment = mGirlFragment;
      if (null != firstFragment) {
        addFragment(firstFragment);
      }
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_girl, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);
    int id = item.getItemId();
    if (id == R.id.action_save) {
      //调用保存的方法
      mGirlFragment.onSave();
      //调用分享的方法
    } else if (id == R.id.action_share) {
      //mGirlFragment.showShare();
    }
    return true;
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
      finishActivity();
      return false;
    } else {
      return super.onKeyDown(keyCode, event);
    }
  }

  private void finishActivity() {
    finish();
    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
