package com.river.image.module.picture.view.home;

import android.content.Context;
import android.view.ViewGroup;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.river.image.bean.GirlsBean;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ImagesAdapter extends RecyclerArrayAdapter<GirlsBean.ResultsEntity> {
  /**
   * Constructor
   *
   * @param context The current context.
   */
  public ImagesAdapter(Context context) {
    super(context);
  }

  @Override public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
    return new ImagesHolder(parent);
  }
}
