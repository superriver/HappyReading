package com.river.image.module.joke;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.bean.JokeBean;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import com.river.image.callback.MyOnScrollListener;
import com.river.image.callback.OnLoadMoreListener;
import com.river.image.common.DataType;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.presenter.IJokeListPresenter;
import com.river.image.module.joke.presenter.IJokeListPresenterImpl;
import com.river.image.module.joke.view.IJokeListView;
import com.river.image.widget.expandablelayout.ExpandableLayoutListView;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class TextJokeFragment extends BaseFragment<IJokeListPresenter>
    implements IJokeListView,OnLoadMoreListener{
  @BindView(R.id.listview) ExpandableLayoutListView listView;
  private List<ContentlistBean> data = new ArrayList<>();
  private TextJokesAdapter mJokeAdapter;
  private View mFooter;

  @Override protected int getLayoutId() {
    return R.layout.fragment_text_joke;
  }

  @Override protected void initData() {
    Log.d("TAG","TextJokeFragment");
    mPresenter = new IJokeListPresenterImpl(this, ApiConfig.TEXT_JOKE);
    // mPresenter.startLoadData(ApiConfig.TEXT_JOKE);
  }

  private void initList(List<ContentlistBean> datas) {
    mJokeAdapter = new TextJokesAdapter(getActivity(),R.layout.item_jokes_list,datas);
    mFooter = LayoutInflater.from(getActivity()).inflate(R.layout.load_more_layout,null);
    MyOnScrollListener myOnScrollListener = new MyOnScrollListener(mFooter);
    myOnScrollListener.setScrollListener(this);
    listView.setOnScrollListener(myOnScrollListener);
    listView.addFooterView(mFooter);
    listView.setAdapter(mJokeAdapter);
  }

  @Override public void updateJokeList(JokeBean jokeBean, String type) {
    data.addAll(jokeBean.showapi_res_body.contentlist);
    if (null != data) {
      initList(data);
    }
    switch (type) {
      case DataType.DATA_LOAD_MORE_SUCCESS:
        mJokeAdapter.addMoreData(data);
        break;
      case DataType.DATA_LOAD_MORE_FAIL:

        break;
      case DataType.DATA_REFRESH_SUCCESS:
       // mJokeAdapter.setData(data);
        break;
      case DataType.DATA_REFRESH_FAIL:
        break;
    }
  }

  @Override public void loadMore() {
    KLog.d("TAG","TextJokeFragment-->");
    mPresenter.loadMoreData();
  }


}
