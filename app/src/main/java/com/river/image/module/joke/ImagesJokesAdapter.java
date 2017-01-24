package com.river.image.module.joke;

import android.content.Context;
import android.view.ViewGroup;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
/**
 * Created by Administrator on 2016/9/14.
 */
public class ImagesJokesAdapter extends RecyclerArrayAdapter<ContentlistBean> {
  /**
   * Constructor
   *
   * @param context The current context.
   */
  public ImagesJokesAdapter(Context context) {
    super(context);
  }

  @Override public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
    return new ImagesJokesHolder(parent);
  }
}
