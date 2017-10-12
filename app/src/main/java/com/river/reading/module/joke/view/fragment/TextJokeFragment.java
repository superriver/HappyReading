package com.river.reading.module.joke.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import butterknife.BindView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.river.image.R;
import com.river.reading.base.BaseFragment;
import com.river.reading.bean.JokeBean;
import com.river.reading.callback.OnItemClickAdapter;
import com.river.reading.callback.OnLoadMoreListener;
import com.river.reading.common.DataType;
import com.river.reading.event.MessageEvent;
import com.river.reading.http.ApiConfig;
import com.river.reading.module.joke.presenter.IJokeListPresenter;
import com.river.reading.module.joke.presenter.IJokeListPresenterImpl;
import com.river.reading.module.joke.view.IJokeListView;
import com.river.reading.module.joke.view.activity.TextJokeDetailActivity;
import com.river.reading.module.news.ui.adapter.BaseRecyclerAdapter;
import com.river.reading.module.news.ui.adapter.BaseRecyclerViewHolder;
import com.river.reading.utils.UnitTransform;
import com.socks.library.KLog;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/7.
 */

public class TextJokeFragment extends BaseFragment<IJokeListPresenter> implements IJokeListView {
  // @BindView(R.id.listview) ExpandableLayoutListView listView;
  private List<JokeBean.ShowapiResBodyBean.ContentlistBean> datas=null;
  private BaseRecyclerAdapter<JokeBean.ShowapiResBodyBean.ContentlistBean> mBaseRecyclerAdapter;
  @BindView(R.id.joke_recycler_view) EasyRecyclerView mEasyRecyclerView;

  @Override protected int getLayoutId() {
    return R.layout.fragment_text_joke;
  }

  @Override protected void initData() {
    mPresenter = new IJokeListPresenterImpl(this, ApiConfig.TEXT_JOKE);
    // mPresenter.startLoadData(ApiConfig.TEXT_JOKE);
  }

  private void initList(List<JokeBean.ShowapiResBodyBean.ContentlistBean> datas) {
    mBaseRecyclerAdapter = new BaseRecyclerAdapter<JokeBean.ShowapiResBodyBean.ContentlistBean>(getActivity(), datas) {
      @Override public int getItemLayoutId(int viewType) {
        return R.layout.item_jokes_list;
      }

      @Override
      public void bindData(BaseRecyclerViewHolder holder, int position, JokeBean.ShowapiResBodyBean.ContentlistBean item) {

        holder.getTextView(R.id.tv_joke_title).setText(item.title);
      }
    };

    mEasyRecyclerView.setAdapter(mBaseRecyclerAdapter);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mEasyRecyclerView.setLayoutManager(linearLayoutManager);
    DividerDecoration itemDecoration =
        new DividerDecoration(Color.GRAY, UnitTransform.dip2px(getActivity(), 1f),
            UnitTransform.dip2px(getActivity(), 1f), 0);

    mEasyRecyclerView.addItemDecoration(itemDecoration);
    mEasyRecyclerView.setRefreshListener(() -> mEasyRecyclerView.postDelayed(() -> {
      mPresenter.refreshData();
    }, 1000));
    mBaseRecyclerAdapter.setOnLoadMoreListener(10, new OnLoadMoreListener() {
      @Override public void loadMore() {
        mPresenter.loadMoreData();
      }
    });
    mBaseRecyclerAdapter.setOnItemClickListener(new OnItemClickAdapter() {
      @Override public void onItemClick(View view, int position) {
        Intent intent = new Intent(mActivity, TextJokeDetailActivity.class);
        EventBus.getDefault().postSticky(new MessageEvent<>(position, datas.get(position)));
        startActivity(intent);
      }
    });
  }

  @Override public void updateJokeList(JokeBean jokeBean,String error, String type) {
    datas=jokeBean.showapi_res_body.contentlist;
    KLog.d("huang","type---"+type);
    if (null != datas) {
      initList(datas);
    }
    switch (type) {
      case DataType.DATA_LOAD_MORE_SUCCESS:
        mBaseRecyclerAdapter.loadMoreSuccess();
        mBaseRecyclerAdapter.addMoreData(datas);
        break;
      case DataType.DATA_LOAD_MORE_FAIL:
        mBaseRecyclerAdapter.loadMoreFailed(error);
        break;
      case DataType.DATA_REFRESH_SUCCESS:
        mBaseRecyclerAdapter.enableLoadMore(true);
        mBaseRecyclerAdapter.setData(datas);
        break;
      case DataType.DATA_REFRESH_FAIL:
        mBaseRecyclerAdapter.enableLoadMore(false);
        mBaseRecyclerAdapter.showEmptyView(true,error);
        mBaseRecyclerAdapter.notifyDataSetChanged();
        break;
    }
  }
}
