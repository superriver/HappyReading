package com.river.image.module.picture.view.home;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.river.image.R;
import com.river.image.base.BaseActivity;
import com.river.image.base.BaseFragment;

/**
 * Created by Administrator on 2016/9/12.
 */
public class HomeActivity extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.fab) FloatingActionButton mFab;
  @Override protected int getContentViewId() {
    return R.layout.activity_main;
  }

  @Override protected int getFragmentContentId() {
    return R.id.image_fragment;
  }

  @Override protected void initView() {
    mToolbar.setTitle("Girls");
    setSupportActionBar(mToolbar);
    if (null == getSupportFragmentManager().getFragments()) {
      BaseFragment firstFragment = getFirstFragment();
      if (null != firstFragment) {
        addFragment(firstFragment);
      }
    }
  }

 // @Override protected void onCreate(Bundle savedInstanceState) {
 //   super.onCreate(savedInstanceState);
 //
 //   //Subscription sb= RxView.clicks(mFab).subscribe(new Action1<Void>() {
 //   //  @Override public void call(Void aVoid) {
 //   //    KLog.d("TAG","Rx");
 //   //  }
 //   //});
 ////   Pair<String,Integer> stringIntegerPair;
 // }
  protected BaseFragment getFirstFragment() {
    return ImageFragment.getInstance();
  }

}
