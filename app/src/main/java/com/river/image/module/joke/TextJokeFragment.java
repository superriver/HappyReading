package com.river.image.module.joke;

import android.widget.AbsListView;
import butterknife.BindView;
import com.river.app.expandablelayout.library.ExpandableLayoutListView;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.bean.JokeBean;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import com.river.image.callback.OnLoadMoreListener;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.presenter.IJokeListPresenter;
import com.river.image.module.joke.presenter.IJokeListPresenterImpl;
import com.river.image.module.joke.view.IJokeListView;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class TextJokeFragment extends BaseFragment<IJokeListPresenter>
    implements IJokeListView, OnLoadMoreListener {
  private String mType;
  private int mPosition;
  private static final String JOKE_TYPE = "joke_type";
  private static final String POSITION = "position";
  @BindView(R.id.listview) ExpandableLayoutListView listView;
  private List<ContentlistBean> mContentlist;
  private TextJokesAdapter mJokeAdapter;
  private int visibleLastIndex;

  @Override protected int getLayoutId() {
    return R.layout.fragment_text_joke;
  }

  @Override protected void initData() {
    mPresenter = new IJokeListPresenterImpl(this);
    mPresenter.startLoadData(ApiConfig.TEXT_JOKE);
    initView();
  }

  private void initView() {
    mContentlist = new ArrayList<>();
    mJokeAdapter = new TextJokesAdapter(getActivity(), R.layout.item_jokes_list, mContentlist);
    listView.setAdapter(mJokeAdapter);
    listView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
          KLog.d("TAG", "visibleLastIndex=" + visibleLastIndex);
          KLog.d("TAG", "view.getLastVisiblePosition=" + view.getLastVisiblePosition());
          KLog.d("TAG", "view.getCount()-1=" + (view.getCount() - 1));
          if (visibleLastIndex == view.getCount() - 1) {
            loadMore();
          }
        }
      }

      @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
        Integer position = -1;
        visibleLastIndex = visibleItemCount + firstVisibleItem - 1;
      }
    });
  }

  @Override public void updateJokeList(JokeBean jokeBean, String type) {
    if (null != jokeBean) {
      mContentlist.addAll(jokeBean.showapi_res_body.contentlist);
    }
    //switch (type) {
    //  case DataType.DATA_LOAD_SUCCESS:
    //     mContentlist.addAll(jokeBean.showapi_res_body.contentlist);
    //   // mJokeAdapter.addAll(mContentlist);
    //   // mJokeAdapter.addAll(jokeBean.showapi_res_body.contentlist);
    //    break;
    //  case DataType.DATA_LOAD_FAIL:
    //
    //    break;
    //  case DataType.DATA_REFRESH_SUCCESS:
    //   // mJokeAdapter.clear();
    //    //mJokeAdapter.addAll(jokeBean.showapi_res_body.contentlist);
    //    break;
    //  case DataType.DATA_REFRESH_FAIL:
    //    break;
    //}
  }

  @Override public void loadMore() {
    mPresenter.loadMoreData();
    mJokeAdapter.notifyDataSetChanged();
  }
}
