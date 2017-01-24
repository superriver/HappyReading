package com.river.image.module.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.bean.JokeBean;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.presenter.IJokeListPresenter;
import com.river.image.module.joke.presenter.IJokeListPresenterImpl;
import com.river.image.module.joke.view.IJokeListView;
import com.river.image.module.joke.view.image.ImageDetailActivity;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7.
 */

public class ImagesJokeFragment extends BaseFragment<IJokeListPresenter> implements IJokeListView {
  private String mType;
  private static final String JOKE_TYPE = "joke_type";
  @BindView(R.id.joke_recycler_view) EasyRecyclerView mRecyclerView;
  private ArrayList<ContentlistBean> mImageList;
  @Override protected int getLayoutId() {
    return R.layout.fragment_image_joke;
  }

  @Override protected void initData() {
    Bundle bundle = getArguments();
    String type=bundle.getString(JOKE_TYPE);
    mPresenter = new IJokeListPresenterImpl(this);
    mPresenter.startLoadData(type, "20", "1", ApiConfig.SHOWAPI_APPID, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void updateJokeList(JokeBean jokeBean) {
    mImageList = new ArrayList<>();
    mImageList.addAll(jokeBean.showapi_res_body.contentlist);
    ImagesJokesAdapter imagesJokesAdapter = new ImagesJokesAdapter(getContext());
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    mRecyclerView.setAdapter(imagesJokesAdapter);
    imagesJokesAdapter.addAll(mImageList);
    imagesJokesAdapter.setOnItemClickListener(position -> {
      Intent intent = new Intent(mActivity, ImageDetailActivity.class);
     // RxBus.get().post("imageJoke",mImageList);
    //  EventBus.getDefault().post(new MessageEvent(position,mImageList));
      intent.putParcelableArrayListExtra("image",mImageList);
      intent.putExtra("current",position);
      startActivity(intent);
    });
  }

}
