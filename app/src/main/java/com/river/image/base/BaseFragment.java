package com.river.image.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{
  protected T mPresenter;
  protected BaseActivity mActivity;
  TextView mTextViewNet;
  protected int mLayout=0;
  protected View mFragmentRootView;
  public BaseActivity getHoldActvity(){
    return  mActivity;
  }
  public Unbinder unbinder;
  protected boolean isConnected;
  protected SparseArray<View> mViews;
  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.mActivity = (BaseActivity) context;
    mViews=new SparseArray<>();
  }

  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mFragmentRootView=mViews.get(getLayoutId());
    if(mFragmentRootView==null) {
      mFragmentRootView = inflater.inflate(getLayoutId(), container, false);
      mViews.put(getLayoutId(),mFragmentRootView);
      unbinder = ButterKnife.bind(this, mFragmentRootView);
    }
    return mFragmentRootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
      initData();
  }


  //protected abstract void initRecycleView();

  protected abstract void initData();
  protected abstract int getLayoutId();

  @Override public void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }
}
