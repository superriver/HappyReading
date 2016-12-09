package com.river.image.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.socks.library.KLog;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{
  protected T mPresenter;
  protected BaseActivity mActivity;
  protected abstract int getLayoutId();
  protected int mLayout=0;
  protected View mFragmentRootView;
  public BaseActivity getHoldActvity(){
    return  mActivity;
  }
  public Unbinder unbinder;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.mActivity = (BaseActivity) context;
  }

  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if(mFragmentRootView==null) {
      mFragmentRootView = inflater.inflate(getLayoutId(), container, false);
      unbinder = ButterKnife.bind(this, mFragmentRootView);
      KLog.d("TAG","BaseFragment");
    }
    initData();
    return mFragmentRootView;
  }

  //protected abstract void initRecycleView();

  protected abstract void initData();

  @Override public void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }
}
