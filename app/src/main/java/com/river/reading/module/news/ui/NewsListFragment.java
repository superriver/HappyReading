package com.river.reading.module.news.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.river.image.R;
import com.river.reading.base.BaseFragment;
import com.river.reading.bean.NewsBean;
import com.river.reading.bean.NewsBean.ShowapiResBodyBean.PageBean.ContentBean;
import com.river.reading.callback.OnItemClickAdapter;
import com.river.reading.callback.OnLoadMoreListener;
import com.river.reading.common.DataType;
import com.river.reading.module.news.presenter.INewsListPresenter;
import com.river.reading.module.news.presenter.INewsListPresenterImpl;
import com.river.reading.module.news.ui.adapter.BaseRecyclerAdapter;
import com.river.reading.module.news.ui.adapter.BaseRecyclerViewHolder;
import com.river.reading.module.news.view.INewsListView;
import com.river.reading.utils.UnitTransform;
import com.socks.library.KLog;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class NewsListFragment extends BaseFragment<INewsListPresenter> implements INewsListView {
  protected static final String NEWS_CHANNEL_ID = "news_id";
  protected static final String NEWS_CHANNEL_NAME = "news_name";
  protected static final String NEWS_CONTENT = "news_content";
  protected static final String POSITION = "position";
  protected String mNewsChannelId;
  protected String mNewsChannelName;
  @BindView(R.id.news_recycler_view) EasyRecyclerView mEasyRecyclerView;
  protected List<ContentBean> contentList;
  private BaseRecyclerAdapter<ContentBean> mBaseRecyclerAdapter;

  @Override protected int getLayoutId() {
    return R.layout.fragment_news;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mNewsChannelId = getArguments().getString(NEWS_CHANNEL_ID);
      mNewsChannelName = getArguments().getString(NEWS_CHANNEL_NAME);
    }
  }

  @Override protected void initData() {
    mPresenter = new INewsListPresenterImpl(this);
    mPresenter.startLoadData(mNewsChannelId, mNewsChannelName,1);
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

  @Override public void updateNewsList(NewsBean newsBean,String msg,String type) {
    contentList=newsBean.showapi_res_body.pagebean.contentlist;
    KLog.d("huang","---"+contentList.size());
    if(mBaseRecyclerAdapter==null){
      initNewsList(contentList);
    }
    switch (type) {
      case DataType.DATA_LOAD_MORE_SUCCESS:
        mBaseRecyclerAdapter.loadMoreSuccess();
        mBaseRecyclerAdapter.addMoreData(contentList);
        break;
      case DataType.DATA_LOAD_MORE_FAIL:
        //mBaseRecyclerAdapter.showEmptyView(true,"加载失败");
        mBaseRecyclerAdapter.loadMoreFailed(msg);
        break;
      case DataType.DATA_REFRESH_SUCCESS:
        mBaseRecyclerAdapter.enableLoadMore(true);
        mBaseRecyclerAdapter.setData(contentList);
        break;
      case DataType.DATA_REFRESH_FAIL:
        mBaseRecyclerAdapter.enableLoadMore(false);
        mBaseRecyclerAdapter.showEmptyView(true,msg);
        mBaseRecyclerAdapter.notifyDataSetChanged();
        break;
    }
  }

  public void initNewsList(List<ContentBean> contentBean) {
    mBaseRecyclerAdapter = new BaseRecyclerAdapter<ContentBean>(getActivity(), contentBean) {
      @Override public int getItemLayoutId(int viewType) {
        return R.layout.item_news_list;
      }

      @Override
      public void bindData(BaseRecyclerViewHolder holder, int position, ContentBean item) {
        if (item.imageurls.size() == 0) {
          holder.getImageView(R.id.iv_news_summary_photo).setImageResource(R.drawable.ic_loading);
        } else {
          Glide.with(getContext())
              .load(item.imageurls.get(0).url)
              .placeholder(R.drawable.ic_loading)
              .error(R.drawable.ic_fail)
              .diskCacheStrategy(DiskCacheStrategy.SOURCE)
              .into(holder.getImageView(R.id.iv_news_summary_photo));
        }
       // KLog.d("TAG","item.title->"+item.title);
        holder.getTextView(R.id.tv_news_summary_title).setText(item.title);
        holder.getTextView(R.id.tv_news_summary_digest).setText(item.desc);
        holder.getTextView(R.id.tv_news_summary_ptime).setText(item.pubDate);
      }
    };
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mEasyRecyclerView.setLayoutManager(linearLayoutManager);
    DividerDecoration itemDecoration =
        new DividerDecoration(Color.GRAY, UnitTransform.dip2px(getActivity(), 1f),
            UnitTransform.dip2px(getActivity(), 1f), 0);

    mEasyRecyclerView.addItemDecoration(itemDecoration);
    mEasyRecyclerView.setAdapter(mBaseRecyclerAdapter);

    mBaseRecyclerAdapter.setOnItemClickListener(new OnItemClickAdapter() {
      @Override public void onItemClick(View view, int position) {
        // 跳转到新闻详情
        if (!TextUtils.isEmpty(mBaseRecyclerAdapter.getData().get(position).desc)) {
          Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
          Bundle bundle = new Bundle();
          bundle.putSerializable(NEWS_CONTENT, mBaseRecyclerAdapter.getData().get(position));
          intent.putExtras(bundle);
          startActivity(intent);
        }
      }
    });
    mEasyRecyclerView.setRefreshListener(() -> mEasyRecyclerView.postDelayed(() -> {
      mPresenter.refreshData();
    }, 1000));

    mBaseRecyclerAdapter.setOnLoadMoreListener(10, new OnLoadMoreListener() {
      @Override public void loadMore() {
        mPresenter.loadMoreData();
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
