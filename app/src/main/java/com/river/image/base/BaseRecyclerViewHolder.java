package com.river.image.base;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.river.image.R;
import com.river.image.bean.NewsBean;

/**
 * Created by Administrator on 2016/11/3.
 */

public class BaseRecyclerViewHolder extends BaseViewHolder<NewsBean.ShowapiResBodyBean.PageBean.ContentBean>{
  private ImageView mImageView;
  private TextView mNewsTitle;
  private TextView mNewsDigest;
  private TextView mNewsTime;
  public BaseRecyclerViewHolder(ViewGroup parent) {
    super(parent, R.layout.item_news_list);
    mImageView=$(R.id.iv_news_summary_photo);
    mNewsTitle=$(R.id.tv_news_summary_title);
    mNewsDigest=$(R.id.tv_news_summary_digest);
    mNewsTime=$(R.id.tv_news_summary_ptime);
  }

  @Override public void setData(NewsBean.ShowapiResBodyBean.PageBean.ContentBean data) {
    super.setData(data);
    if(data.imageurls.size()==0){
      mImageView.setImageResource(R.drawable.ic_loading);
    }else{
      Glide.with(getContext()).load(data.imageurls.get(0).url).placeholder(R.drawable.ic_loading).error(R.drawable.ic_fail).diskCacheStrategy(
          DiskCacheStrategy.SOURCE).into(mImageView);
    }
    mNewsTitle.setText(data.title);
    mNewsDigest.setText(data.desc);
    mNewsTime.setText(data.pubDate);
  }
}
