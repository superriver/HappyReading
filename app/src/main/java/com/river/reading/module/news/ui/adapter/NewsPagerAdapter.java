package com.river.reading.module.news.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.river.reading.base.BaseFragment;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {

  private List<BaseFragment> mFragments;
  private List<String> mFragmentTitles;
  public NewsPagerAdapter(FragmentManager fm,List<BaseFragment> mFragments,List<String> fragmentTitles) {
    super(fm);
    this.mFragments=mFragments;
    mFragmentTitles=fragmentTitles;
  }

  @Override
  public int getCount() {
    return mFragmentTitles == null ? 0 : mFragmentTitles.size();
  }

  /**
   * Return the Fragment associated with a specified position.
   */
  @Override public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mFragmentTitles.get(position);
  }


  //public void setData(List<BaseFragment> baseFragments,List<String> titleList){
  //  mFragments=baseFragments;
  //  mFragmentTitles=titleList;
  //}
  //public void clear() {
  //  mFragments.clear();
  //  mFragmentTitles.clear();
  //}

}
