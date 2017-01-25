package com.river.image.module.news.ui;

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
import com.river.image.base.BaseFragment;
import com.river.image.bean.NewsBean;
import com.river.image.bean.NewsBean.ShowapiResBodyBean.PageBean.ContentBean;
import com.river.image.callback.OnItemClickAdapter;
import com.river.image.callback.OnLoadMoreListener;
import com.river.image.common.DataType;
import com.river.image.module.news.presenter.INewsListPresenter;
import com.river.image.module.news.presenter.INewsListPresenterImpl;
import com.river.image.module.news.ui.adapter.BaseRecyclerAdapter;
import com.river.image.module.news.ui.adapter.BaseRecyclerViewHolder;
import com.river.image.module.news.view.INewsListView;
import com.river.image.utils.UnitTransform;
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

  //private BaseRecyclerAdapter mBaseRecyclerAdapter;
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
    //SimpleDateFormat fmt;
    //fmt = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("zh", "CN"));
    //String sysDatetime = fmt.format(new Date());
    //KLog.d("TAG", "time1-------->" + sysDatetime);
    mPresenter = new INewsListPresenterImpl(this);
    mPresenter.startLoadData(mNewsChannelId, mNewsChannelName);
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

  @Override public void updateNewsList(NewsBean newsBean,String type) {
    List<ContentBean> contentlistBeenList = newsBean.showapi_res_body.pagebean.contentlist;
    if(mBaseRecyclerAdapter==null){
      initNewsList(contentlistBeenList);
    }
    switch (type) {
      case DataType.DATA_LOAD_SUCCESS:
        mBaseRecyclerAdapter.addMoreData(contentlistBeenList);
        break;
      case DataType.DATA_LOAD_FAIL:

        break;
      case DataType.DATA_REFRESH_SUCCESS:
        mBaseRecyclerAdapter.loadMoreSuccess();
        mBaseRecyclerAdapter.addMoreData(contentlistBeenList);
        break;
      case DataType.DATA_REFRESH_FAIL:

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
