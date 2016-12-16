package com.river.image.module.news.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.river.image.R;
import com.river.image.annotation.ActivityFragmentInject;
import com.river.image.base.BaseActivity;
import com.river.image.base.BaseFragment;
import com.river.image.bean.NewsChannel;
import com.river.image.bean.NewsChannelBean;
import com.river.image.module.joke.JokeActivity;
import com.river.image.module.news.presenter.INewsChannelPresenter;
import com.river.image.module.news.presenter.INewsChannelPresenterImpl;
import com.river.image.module.news.ui.adapter.NewsPagerAdapter;
import com.river.image.module.news.view.INewsChannelView;
import com.river.image.module.picture.view.home.HomeActivity;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

/**
 * Creatd by Administrator on 2016/10/13.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_news)
public class NewsActivity extends BaseActivity<INewsChannelPresenter> implements INewsChannelView {
  @BindView(R.id.magic_indicator) MagicIndicator mIndicator;
  @BindView(R.id.news_viewpager) ViewPager mViewPager;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  private NewsPagerAdapter mPagerAdapter;
  private List<String> mTitleList = new ArrayList<>();
  private List<BaseFragment> mFragments = new ArrayList<>();
  private DrawerLayout drawerLayout;
  public static Intent newIntent(Context context, String title) {
    Intent intent = new Intent(context, NewsActivity.class);
    intent.putExtra("title", title);
    return intent;
  }
  @Override protected int getFragmentContentId() {
    return 0;
  }

  @Override protected void initView() {
    mPresenter = new INewsChannelPresenterImpl(this);
    mToolbar.setTitle("新闻");
    setSupportActionBar(mToolbar);
    initDrawLayout();
  }

  //通过网络获取新闻频道数据
  @Override public void getDataByNet(NewsChannel newsChannel) {
    //mPagerAdapter.clear();
    //mTabLayout.removeAllTabs();
    List<NewsChannel.NewsChannelBody.ChannelListBean> channelLists =
        newsChannel.res_body.channelList;
    for (NewsChannel.NewsChannelBody.ChannelListBean channelListBean : channelLists) {
      mFragments.add(
          NewsListFragment.newsInstance(channelListBean.name, channelListBean.channelId));
      //mTabLayout.addTab(mTabLayout.newTab().setText(channelListBean.name));
      mTitleList.add(channelListBean.name);
    }
    // mPagerAdapter.setData(mFragments,mTitleList);
    initViewPager();
  }

  //通过数据库获取新闻频道数据
  @Override public void getDataByDB(List<NewsChannelBean> channelBeanList) {
    //mPagerAdapter.clear();
    //   mTabLayout.removeAllTabs();
    for (NewsChannelBean channelBean : channelBeanList) {
      mFragments.add(
          NewsListFragment.newsInstance(channelBean.getChannelId(), channelBean.getName()));
      mTitleList.add(channelBean.getName());
    }

    initViewPager();
  }
  public void initViewPager() {
    mPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(),mFragments,mTitleList);
    mViewPager.setAdapter(mPagerAdapter);
    mIndicator.setBackgroundColor(Color.parseColor("#d43d3d"));
    CommonNavigator mCommonNavigator = new CommonNavigator(this);
    mCommonNavigator.setSkimOver(true);
    mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
      @Override public int getCount() {
        return mTitleList == null ? 0 :
            mTitleList.size();
      }

      @Override public IPagerTitleView getTitleView(Context context, int i) {
        ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
        clipPagerTitleView.setText(mTitleList.get(i));
        clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
        clipPagerTitleView.setClipColor(Color.WHITE);
        clipPagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(i));
        return clipPagerTitleView;
      }

      @Override public IPagerIndicator getIndicator(Context context) {
        return null;
      }
    });
    mIndicator.setNavigator(mCommonNavigator);
    ViewPagerHelper.bind(mIndicator, mViewPager);
    //mPagerAdapter =new NewsPagerAdapter(getChildFragmentManager(),mFragments,mTitleList);
    //if(mViewPager!=null){
    //  mViewPager.setAdapter(mPagerAdapter);
    //}
    //mPagerAdapter.setData(mFragments,mTitleList);
    // mViewPager.setCurrentItem(0,false);
    // mViewPager.setOffscreenPageLimit(2);
    // mTabLayout.setupWithViewPager(mViewPager);
    // mTabLayout.setScrollPosition(0, 0, true);
    // mTabLayout.set
    //setOnTabSelectEvent(mViewPager, mTabLayout);
  }
  public void initDrawLayout() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
    indicator.setColor(Color.WHITE);
    getSupportActionBar().setHomeAsUpIndicator(indicator);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerLayout.setScrimColor(Color.TRANSPARENT);
    drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
      @Override public void onDrawerSlide(View drawerView, float slideOffset) {
        if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
          indicator.setProgress(slideOffset);
        }
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawer(GravityCompat.START);
      } else {
        drawerLayout.openDrawer(GravityCompat.START);
      }
    }
    return true;
  }

  public void onClick(View view) {
    if (view instanceof TextView) {
      String title = ((TextView) view).getText().toString();
      int flag = view.getId();
      switch (flag) {
        case R.id.action_news:
          startActivity(NewsActivity.newIntent(this, title));
          break;
        case R.id.action_photo:
          startActivity(HomeActivity.newIntent(this, title));
          break;
        case R.id.action_jokes:
          startActivity(JokeActivity.newIntent(this, title));
          break;
      }
    } else if (view.getId() == R.id.userInfo) {
      // startActivity(UniversalActivity.newIntent(this, "个人中心"));
    }
  }
}
