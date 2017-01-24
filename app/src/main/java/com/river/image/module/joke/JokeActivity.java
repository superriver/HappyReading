package com.river.image.module.joke;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.river.image.R;
import com.river.image.annotation.ActivityFragmentInject;
import com.river.image.base.BaseActivity;
import com.river.image.base.BaseFragment;
import com.river.image.base.BaseFragmentAdapter;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.presenter.IJokePresenterImpl;
import com.river.image.module.joke.view.IJokeView;
import java.util.ArrayList;
import java.util.List;

@ActivityFragmentInject(
    contentViewId = R.layout.activity_joke,
    toolbarTitle = R.string.joke,
    hasNavigationView = true
)
public class JokeActivity extends BaseActivity implements IJokeView{
  private static final String JOKE_TYPE = "joke_type";
  private static final String POSITION = "position";

  private String[] tabTitle = new String[] { "文本笑话", "图片笑话", "动态图片" };
  @BindView(R.id.all_viewpager)  ViewPager mViewPager;
  @BindView(R.id.tabs) TabLayout mTabLayout;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  public static Intent newIntent(Context context, String title) {
    Intent intent = new Intent(context, JokeActivity.class);
    intent.putExtra("title", title);
    return intent;
  }
  @Override protected int getFragmentContentId() {
    return 0;
  }

  @Override protected void initView() {
    mPresenter = new IJokePresenterImpl(this);
  }

  @Override public void initViewPager() {
     TextJokeFragment mTextJokeFragment;
     ImagesJokeFragment mImageJokeFragment, mDynamicJokeFragment;
    setSupportActionBar(mToolbar);
   // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle(getIntent().getStringExtra("title"));
    // ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mTitles,mLayoutList);
    List<BaseFragment> fragments = new ArrayList<>();
    mTextJokeFragment = new TextJokeFragment();

    mImageJokeFragment = new ImagesJokeFragment();
    Bundle imageBundle = new Bundle();
    imageBundle.putString(JOKE_TYPE, ApiConfig.IMG_JOKE);
    mImageJokeFragment.setArguments(imageBundle);

    mDynamicJokeFragment = new ImagesJokeFragment();
    Bundle dynamicBundle = new Bundle();
    dynamicBundle.putString(JOKE_TYPE, ApiConfig.DYNAMIC_JOKE);
    mDynamicJokeFragment.setArguments(dynamicBundle);
    fragments.add(mTextJokeFragment);
    fragments.add(mImageJokeFragment);
    fragments.add(mDynamicJokeFragment);
    // 初始化ViewPager
    BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments);
    mViewPager.setAdapter(adapter);
    mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
    mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
    mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[2]));
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.getTabAt(0).setText(tabTitle[0]);
    mTabLayout.getTabAt(1).setText(tabTitle[1]);
    mTabLayout.getTabAt(2).setText(tabTitle[2]);
    //setOnTabSelectEvent(viewPager,tabLayout);
    // mViewPager.setAdapter(mExamplePagerAdapter);
  }


}