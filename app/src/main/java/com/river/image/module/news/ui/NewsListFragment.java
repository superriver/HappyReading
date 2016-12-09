package com.river.image.module.news.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.base.BaseRecyclerAdapter;
import com.river.image.bean.NewsBean;
import com.river.image.http.ApiConfig;
import com.river.image.module.news.presenter.INewsListPresenter;
import com.river.image.module.news.presenter.INewsListPresenterImpl;
import com.river.image.module.news.view.INewsListView;
import com.river.image.utils.UnitTransform;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class NewsListFragment extends BaseFragment<INewsListPresenter> implements INewsListView {
  protected static final String NEWS_CHANNEL_ID = "news_id";
  protected static final String NEWS_CHANNEL_NAME = "news_name";
  protected static final String NEWS_CONTENT="news_content";
  protected static final String POSITION = "position";
  private int mPage=1;
  private String maxSize="20";
  protected String mNewsChannelId;
  protected String mNewsChannelName;
  @BindView(R.id.news_recycler_view) EasyRecyclerView mEasyRecyclerView;
  private static List<NewsBean.ShowapiResBodyBean.PageBean.ContentBean> contentlistBeenList;
  private BaseRecyclerAdapter mBaseRecyclerAdapter;

  @Override protected int getLayoutId() {
    return R.layout.fragment_news;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(getActivity());
    if (getArguments() != null) {
      mNewsChannelId = getArguments().getString(NEWS_CHANNEL_ID);
      mNewsChannelName = getArguments().getString(NEWS_CHANNEL_NAME);
    }
  }

  @Override protected void initData() {
    mPresenter = new INewsListPresenterImpl(this);
    mPresenter.startLoadData(mNewsChannelId, mNewsChannelName, "20", "1", "1", "0", "1",
            ApiConfig.SHOWAPI_APPID, null, null, ApiConfig.SHOWAPI_SIGN);

  }

  public static NewsListFragment newsInstance(String id, String title) {
    NewsListFragment newsFragment = new NewsListFragment();
    Bundle bundle = new Bundle();
    bundle.putString(NEWS_CHANNEL_ID, id);
    bundle.putString(NEWS_CHANNEL_NAME, title);
    //  bundle.putInt(POSITION,position);
    newsFragment.setArguments(bundle);
    return newsFragment;
  }


  @Override public void updateNewsList(NewsBean newsBean) {
    contentlistBeenList = newsBean.showapi_res_body.pagebean.contentlist;
    initNewsList();
  }

  public void initNewsList() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mEasyRecyclerView.setLayoutManager(linearLayoutManager);
    DividerDecoration itemDecoration =
        new DividerDecoration(Color.GRAY, UnitTransform.dip2px(getActivity(), 3f),
            UnitTransform.dip2px(getActivity(), 3f), 0);
    mBaseRecyclerAdapter = new BaseRecyclerAdapter(getContext());
    mEasyRecyclerView.addItemDecoration(itemDecoration);
    mEasyRecyclerView.setAdapter(mBaseRecyclerAdapter);
    mBaseRecyclerAdapter.addAll(contentlistBeenList);
    mBaseRecyclerAdapter.setMore(R.layout.load_more_layout,
        new RecyclerArrayAdapter.OnMoreListener() {
          @Override public void onMoreShow() {
            if(contentlistBeenList.size()%20==0) {
              mPage++;
              mPresenter.startLoadData(mNewsChannelId, mNewsChannelName, "20", "1", "1", "0", String.valueOf(mPage),
                  ApiConfig.SHOWAPI_APPID, null, null, ApiConfig.SHOWAPI_SIGN);
            }
          }

          @Override public void onMoreClick() {

          }
        });
    mBaseRecyclerAdapter.setNoMore(R.layout.no_more_layout);
    mEasyRecyclerView.setRefreshListener(() -> mEasyRecyclerView.postDelayed(() -> {
      mPresenter.startLoadData(mNewsChannelId, mNewsChannelName, "20", "1", "1", "0", "1",
          ApiConfig.SHOWAPI_APPID, null, null, ApiConfig.SHOWAPI_SIGN);
    },1000));
    mBaseRecyclerAdapter.setOnItemClickListener(position -> {
      if(contentlistBeenList!=null){
          Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
          intent.putExtra(NEWS_CONTENT,contentlistBeenList.get(position));
          startActivity(intent);
      }
    });
  }

  //
  //@Override public void runOnUiThread(Runnable runnable) {
  //  if (null == getActivity()) {
  //    return;
  //  }
  //  getActivity().runOnUiThread(runnable);
  //}
}
