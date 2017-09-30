package com.river.image.module.joke.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.river.image.R;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import com.river.image.widget.PinchImageView;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ImageDetailAdapter extends PagerAdapter{
  private Context mContext;
  private List<ContentlistBean> datas;
  private LayoutInflater mInflater;
  private View mCurrentView;

  public ImageDetailAdapter(Context context, List<ContentlistBean> datas) {
    mContext = context;
    this.datas = datas;
    mInflater=LayoutInflater.from(context);
  }

  /**
   * Return the number of views available.
   */
  @Override public int getCount() {
    return datas.size();
  }

  @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
    super.setPrimaryItem(container, position, object);
    mCurrentView = (View) object;
  }
  public View getPrimaryItem() {
    return mCurrentView;
  }

  @Override public View instantiateItem(ViewGroup container, int position) {
    String imageUrl=datas.get(position).img;
    View view=mInflater.inflate(R.layout.girl_item,container,false);
    PinchImageView pinchImageView = (PinchImageView) view.findViewById(R.id.img);
    Glide.with(mContext).load(imageUrl).placeholder(R.mipmap
    .ic_launcher).thumbnail(0.2f).into(pinchImageView);
    container.addView(view);
    return view;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view==object;
  }
}
