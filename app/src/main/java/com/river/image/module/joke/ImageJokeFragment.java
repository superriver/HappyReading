package com.river.image.module.joke;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.bean.TextJokeBean;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.presenter.IJokeListPresenter;
import com.river.image.module.joke.presenter.IJokeListPresenterImpl;
import com.river.image.module.joke.view.IJokeListView;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class ImageJokeFragment extends BaseFragment<IJokeListPresenter> implements IJokeListView {
  private String mType;
  private static final String JOKE_TYPE = "joke_type";
  @BindView(R.id.joke_recycler_view) EasyRecyclerView mRecyclerView;
  private List<TextJokeBean.ShowapiResBodyBean.ContentlistBean> mContentlist;
  @Override protected int getLayoutId() {
    return R.layout.fragment_image_joke;
  }

  @Override protected void initData() {
    Bundle bundle = getArguments();
    String type=bundle.getString(JOKE_TYPE);
    mPresenter = new IJokeListPresenterImpl(this);
    mPresenter.startLoadData(type, "20", "1", ApiConfig.SHOWAPI_APPID, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void updateJokeList(TextJokeBean textJokeBean) {
    mContentlist = textJokeBean.showapi_res_body.contentlist;
    ImagesJokesAdapter imagesJokesAdapter = new ImagesJokesAdapter(getContext());
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    mRecyclerView.setAdapter(imagesJokesAdapter);
    imagesJokesAdapter.addAll(mContentlist);
  }

}
