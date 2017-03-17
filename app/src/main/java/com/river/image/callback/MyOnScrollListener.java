package com.river.image.callback;

import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
/**
 * Created by Administrator on 2017/3/15.
 */

public class MyOnScrollListener implements AbsListView.OnScrollListener{
  //ListView总共显示多少条
  private int totalItemCount;

  //ListView最后的item项
  private int lastItem;

  //用于判断当前是否在加载
  private boolean isLoading;
  //底部加载更多布局
  private View footer;
  public OnLoadMoreListener mOnloadDataListener;

  public MyOnScrollListener(View footer) {
    this.footer=footer;
  }
  public void setScrollListener(OnLoadMoreListener mOnloadDataListener) {
    this.mOnloadDataListener = mOnloadDataListener;
  }
  @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
    if (!isLoading && lastItem == totalItemCount && SCROLL_STATE_IDLE == scrollState) {
      //显示加载更多
      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override public void run() {
          if(mOnloadDataListener!=null){
            mOnloadDataListener.loadMore();
            //加载完成后操作什么
            isLoading=true;
          }
        }
      },2000);


    }
  }

  @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
      int totalItemCount) {
    lastItem = visibleItemCount + firstVisibleItem;
    this.totalItemCount = totalItemCount;

    if (footer != null) {
      //判断可视Item是否能在当前页面完全显示
      if (visibleItemCount == totalItemCount) {
        // removeFooterView(footerView);
        footer.setVisibility(View.GONE);//隐藏底部布局
      } else {
        // addFooterView(footerView);
        footer.setVisibility(View.VISIBLE);//显示底部布局
      }
    }
  }

}
