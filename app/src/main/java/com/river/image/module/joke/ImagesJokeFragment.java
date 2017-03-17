package com.river.image.module.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import butterknife.BindView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.bean.JokeBean;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import com.river.image.bean.MessageEvent;
import com.river.image.common.DataType;
import com.river.image.module.joke.presenter.IJokeListPresenter;
import com.river.image.module.joke.presenter.IJokeListPresenterImpl;
import com.river.image.module.joke.view.IJokeListView;
import com.river.image.module.joke.view.image.ImageDetailActivity;
import com.socks.library.KLog;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/7.
 */

public class ImagesJokeFragment extends BaseFragment<IJokeListPresenter> implements IJokeListView {
  private String mType;
  private static final String JOKE_TYPE = "joke_type";
  @BindView(R.id.joke_recycler_view) EasyRecyclerView mRecyclerView;
  private ArrayList<ContentlistBean> mImageList;
  private ImagesJokesAdapter mImagesJokesAdapter;

  @Override protected int getLayoutId() {
    return R.layout.fragment_image_joke;
  }

  @Override protected void initData() {
    Bundle bundle = getArguments();
    String type = bundle.getString(JOKE_TYPE);
    mPresenter = new IJokeListPresenterImpl(this,type);
    Log.d("TAG","ImagesJokeFragment");
    //mPresenter.startLoadData(type);
    initRecyclerView();
  }
  private void initRecyclerView() {
    mImageList = new ArrayList<>();
    mImagesJokesAdapter = new ImagesJokesAdapter(getContext());
    //mImagesJokesAdapter.addAll(mImageList);
    StaggeredGridLayoutManager staggeredGridLayoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    mRecyclerView.setAdapter(mImagesJokesAdapter);
    mImagesJokesAdapter.setOnItemClickListener(position -> {
      Intent intent = new Intent(mActivity, ImageDetailActivity.class);
      // RxBus.get().post("imageJoke",mImageList);
       EventBus.getDefault().postSticky(new MessageEvent(position,mImageList));
      //intent.putParcelableArrayListExtra("image", mImageList);
      //intent.putExtra("image",mImageList);
     // intent.putExtra("current", position);
      startActivity(intent);
    });
    mImagesJokesAdapter.setMore(R.layout.load_more_layout,
        new RecyclerArrayAdapter.OnMoreListener() {
          @Override public void onMoreShow() {
            KLog.d("TAG","mImagesJokesAdapter-->");
            mPresenter.loadMoreData();
          }

          @Override public void onMoreClick() {

          }
        });
    mRecyclerView.setRefreshListener(() -> mPresenter.refreshData()

    );
  }
  @Override public void updateJokeList(JokeBean jokeBean, String type) {
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
