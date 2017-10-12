package com.river.reading.module.joke.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.river.image.R;
import com.river.reading.base.BaseFragment;
import com.river.reading.bean.JokeBean;
import com.river.reading.common.DataType;
import com.river.reading.event.MessageEvent;
import com.river.reading.module.joke.view.adapter.ImagesJokesAdapter;
import com.river.reading.module.joke.presenter.IJokeListPresenter;
import com.river.reading.module.joke.presenter.IJokeListPresenterImpl;
import com.river.reading.module.joke.view.IJokeListView;
import com.river.reading.module.joke.view.activity.ImageDetailActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/7.
 */

public class ImagesJokeFragment extends BaseFragment<IJokeListPresenter> implements IJokeListView {
  private String mType;
  private static final String JOKE_TYPE = "joke_type";
  @BindView(R.id.joke_recycler_view) EasyRecyclerView mRecyclerView;
  private List<JokeBean.ShowapiResBodyBean.ContentlistBean> mImageList;
  private ImagesJokesAdapter mImagesJokesAdapter;

  @Override protected int getLayoutId() {
    return R.layout.fragment_image_joke;
  }

  @Override protected void initData() {
    Bundle bundle = getArguments();
    String type = bundle.getString(JOKE_TYPE);
    mPresenter = new IJokeListPresenterImpl(this,type);
    //mPresenter.startLoadData(type);
    initRecyclerView();
  }
  private void initRecyclerView() {
    mImageList = new ArrayList<>();
    mImagesJokesAdapter = new ImagesJokesAdapter(mActivity);
    StaggeredGridLayoutManager staggeredGridLayoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    mRecyclerView.setAdapter(mImagesJokesAdapter);
    mImagesJokesAdapter.setOnItemClickListener(position -> {
      Intent intent = new Intent(mActivity, ImageDetailActivity.class);
      // RxBus.get().post("imageJoke",mImageList);
       EventBus.getDefault().postSticky(new MessageEvent<>(position,mImageList));
      startActivity(intent);
    });
    mImagesJokesAdapter.setMore(R.layout.load_more_layout,
        new RecyclerArrayAdapter.OnMoreListener() {
          @Override public void onMoreShow() {
            mPresenter.loadMoreData();
          }

          @Override public void onMoreClick() {

          }
        });
    mRecyclerView.setRefreshListener(() -> mPresenter.refreshData()

    );
  }
  @Override public void updateJokeList(JokeBean jokeBean, String error,String type) {
    switch (type) {
      case DataType.DATA_LOAD_MORE_SUCCESS:
        mImageList.addAll(jokeBean.showapi_res_body.contentlist);
        mImagesJokesAdapter.addAll(jokeBean.showapi_res_body.contentlist);
        break;
      case DataType.DATA_LOAD_MORE_FAIL:

        break;
      case DataType.DATA_REFRESH_SUCCESS:
        mImageList.clear();
        mImageList.addAll(jokeBean.showapi_res_body.contentlist);
        mImagesJokesAdapter.clear();
        mImagesJokesAdapter.addAll(jokeBean.showapi_res_body.contentlist);
        break;
      case DataType.DATA_REFRESH_FAIL:

        break;
    }
  }
}
