package com.river.reading.module.picture.view.home;

import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.river.image.R;
import com.river.reading.bean.GirlsBean;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ImagesHolder extends BaseViewHolder<GirlsBean.ResultsEntity>{
      private ImageView mImageView;
      public ImagesHolder(ViewGroup parent) {
        super(parent, R.layout.images_item);
        mImageView = $(R.id.iv_images);
      }

  @Override public void setData(GirlsBean.ResultsEntity data) {
    super.setData(data);
    Glide.with(getContext()).load(data.getUrl()).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImageView);
  }
}
