package com.river.image.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter{
  private FragmentManager mFragmentManager;
  private List<BaseFragment> mFragments;
  private List<String> mTitles;
  public BaseFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
    super(fm);
    mFragmentManager = fm;
    mFragments = fragments;
  }
  public void updateFragments(List<BaseFragment> fragments, List<String> titles) {
    for (int i = 0; i < mFragments.size(); i++) {
      final BaseFragment fragment = mFragments.get(i);
      final FragmentTransaction ft = mFragmentManager.beginTransaction();
      if (i > 2) {
        ft.remove(fragment);
        mFragments.remove(i);
        i--;
      }
      ft.commit();
    }
    for (int i = 0; i < fragments.size(); i++) {
      if (i > 2) {
        mFragments.add(fragments.get(i));
      }
    }
    this.mTitles = titles;
    notifyDataSetChanged();
  }
  /**
   * Return the Fragment associated with a specified position.
   */
  @Override public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  /**
   * Return the number of views available.
   */
  @Override public int getCount() {
    return mFragments.size();
  }
}
