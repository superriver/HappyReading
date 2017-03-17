package com.river.image.module.picture.view.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.river.image.R;
import com.river.image.annotation.ActivityFragmentInject;
import com.river.image.base.BaseActivity;
import com.river.image.base.BaseFragment;

/**
 * Created by Administrator on 2016/9/12.
 */
@ActivityFragmentInject(
    contentViewId = R.layout.activity_main,
    toolbarTitle = R.string.photo,
    hasNavigationView = true
)
public class HomeActivity
    extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  // @BindView(R.id.fab) FloatingActionButton mFab;
  private DrawerLayout drawerLayout;

  public static Intent newIntent(Context context, String title) {
    Intent intent = new Intent(context, HomeActivity.class);
    intent.putExtra("title", title);
    return intent;
  }

  @Override protected int getFragmentContentId() {
    return R.id.image_fragment;
  }

  @Override protected void initView() {
    // mToolbar.setTitle("图片");
    setSupportActionBar(mToolbar);
    setTitle(getIntent().getStringExtra("title"));
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
  //   //    KLog.d("TAG","Rx ");
  //   //  }
  //   //});
  ////   Pair<String,Integer> stringIntegerPair;
  // }
  protected BaseFragment getFirstFragment() {
    return ImageFragment.getInstance();
  }
}
