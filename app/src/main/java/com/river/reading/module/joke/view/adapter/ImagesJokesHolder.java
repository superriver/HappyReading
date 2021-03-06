package com.river.reading.module.joke.view.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.river.image.R;
import com.river.reading.bean.JokeBean;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ImagesJokesHolder extends BaseViewHolder<JokeBean.ShowapiResBodyBean.ContentlistBean> {
  private ImageView mImageView;
  private TextView mTextView;
  public ImagesJokesHolder(ViewGroup parent) {
    super(parent, R.layout.item_image_joke);
    mImageView = $(R.id.dongtai);
    mTextView=$(R.id.tv_joke_title);
  }

  @Override public void setData(JokeBean.ShowapiResBodyBean.ContentlistBean data) {
    super.setData(data);
    if(null!=data){
      Glide.with(getContext())
          .load(data.img)
          .asGif()
          .placeholder(R.mipmap.ic_launcher)
          .diskCacheStrategy(DiskCacheStrategy.RESULT)
          .override(100,100)
          .into(mImageView);
      mTextView.setText(data.title);
    }

  }
}
