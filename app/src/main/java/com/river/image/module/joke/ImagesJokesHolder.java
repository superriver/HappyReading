package com.river.image.module.joke;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.river.image.R;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ImagesJokesHolder extends BaseViewHolder<ContentlistBean> {
  private ImageView mImageView;
  private TextView mTextView;
  public ImagesJokesHolder(ViewGroup parent) {
    super(parent, R.layout.item_image_joke);
    mImageView = $(R.id.dongtai);
    mTextView=$(R.id.tv_joke_title);
  }

  @Override public void setData(ContentlistBean data) {
    super.setData(data);
    if(null!=data){
      Glide.with(getContext())
          .load(data.img)
          .asGif()
          .placeholder(R.mipmap.ic_launcher)
          .diskCacheStrategy(DiskCacheStrategy.RESULT)
          .override(100,100)
          .into(mImageView);
      //GifDrawable gifDrawable = new GifDrawable();
      //GifDrawable gifDrawable = new GifDrawable();
      mTextView.setText(data.title);
    }

  }
}
