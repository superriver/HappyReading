package com.river.image.module.news.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.river.image.R;
import com.river.image.annotation.ActivityFragmentInject;
import com.river.image.base.BaseActivity;
import com.river.image.base.BaseFragment;
import com.river.image.bean.NewsChannel;
import com.river.image.bean.NewsChannelBean;
import com.river.image.module.news.presenter.INewsChannelPresenter;
import com.river.image.module.news.presenter.INewsChannelPresenterImpl;
import com.river.image.module.news.ui.adapter.NewsPagerAdapter;
import com.river.image.module.news.view.INewsChannelView;
import java.util.ArrayList;
import java.util.List;

/**
 * Creatd by Administrator on 2016/10/13.
 */

@ActivityFragmentInject(
    contentViewId = R.layout.activity_news,
    toolbarTitle = R.string.news,
    hasNavigationView = true
    )
public class NewsActivity extends BaseActivity<INewsChannelPresenter> implements INewsChannelView {
  @BindView(R.id.all_viewpager) ViewPager mViewPager;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.tab_layout) TabLayout mTabLayout;
  private NewsPagerAdapter mPagerAdapter;
  private List<String> mTitleList = new ArrayList<>();
  private List<BaseFragment> mFragments = new ArrayList<>();

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
  }

  //通过网络获取新闻频道数据
  @Override public void getDataByNet(NewsChannel newsChannel) {
    //mPagerAdapter.clear();
    //mTabLayout.removeAllTabs();
    NewsChannel.NewsChannelBody.ChannelListBean[] channelLists =
        newsChannel.res_body.channelList;
    for (NewsChannel.NewsChannelBody.ChannelListBean channelListBean : channelLists) {
      mFragments.add(
          NewsListFragment.newsInstance(channelListBean.channelId, channelListBean.name));
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
    //mPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(),mFragments,mTitleList);
    //mViewPager.setAdapter(mPagerAdapter);
    //mIndicator.setBackgroundColor(Color.parseColor("#d43d3d"));
    //CommonNavigator mCommonNavigator = new CommonNavigator(this);
    //mCommonNavigator.setSkimOver(true);
    //mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
    //  @Override public int getCount() {
    //    return mTitleList == null ? 0 :
    //        mTitleList.size();
    //  }
    //
    //  @Override public IPagerTitleView getTitleView(Context context, int i) {
    //    ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
    //    clipPagerTitleView.setText(mTitleList.get(i));
    //    clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
    //    clipPagerTitleView.setClipColor(Color.WHITE);
    //    clipPagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(i));
    //    return clipPagerTitleView;
    //  }
    //
    //  @Override public IPagerIndicator getIndicator(Context context) {
    //    return null;
    //  }
    //});
    //mIndicator.setNavigator(mCommonNavigator);
    //ViewPagerHelper.bind(mIndicator, mViewPager);
    if (mViewPager != null) {
      mPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
      mViewPager.setAdapter(mPagerAdapter);
    }
    //mPagerAdapter.setData(mFragments,mTitleList);
    mViewPager.setCurrentItem(0, false);
    mViewPager.setOffscreenPageLimit(2);
    mTabLayout.setupWithViewPager(mViewPager);
  }

}
