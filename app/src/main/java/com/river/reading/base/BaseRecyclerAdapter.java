package com.river.reading.base;

import android.content.Context;
import android.view.ViewGroup;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.river.reading.bean.NewsBean;

/**
 * Created by Administrator on 2016/11/3.
 */

public class BaseRecyclerAdapter extends RecyclerArrayAdapter<NewsBean.ShowapiResBodyBean.PageBean.ContentBean>{
  /**
   * Constructor
   *
   * @param context The current context.
   */
  public BaseRecyclerAdapter(Context context) {
    super(context);
  }

  @Override public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
    return new BaseRecyclerViewHolder(parent);

  }
}
