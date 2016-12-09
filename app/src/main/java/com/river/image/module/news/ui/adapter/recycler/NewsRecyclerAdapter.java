package com.river.image.module.news.ui.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.river.image.R;
import com.river.image.bean.NewsBean;
import com.socks.library.KLog;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
  private LayoutInflater mInflater;
  private List<NewsBean.ShowapiResBodyBean.PageBean.ContentBean> mDatas;
  private Context mContext;
  public NewsRecyclerAdapter(Context context,  List<NewsBean.ShowapiResBodyBean.PageBean.ContentBean> contents)
  {
    mInflater = LayoutInflater.from(context);
    mDatas = contents;
    mContext=context;
    notifyDataSetChanged();
  }
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view=mInflater.inflate(R.layout.item_news_list,parent,false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }


  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    KLog.d("TAG","position-->"+position);
    Glide.with(mContext).load(mDatas.get(position).imageurls.get(0)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.mImageView);
    holder.mNewsTitle.setText(mDatas.get(position).title);
    holder.mNewsDigest.setText(mDatas.get(position).desc);
    holder.mNewsTime.setText(mDatas.get(position).pubDate);
  }


  @Override public int getItemCount() {
    return mDatas.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder{
    private ImageView mImageView;
    private TextView mNewsTitle;
    private TextView mNewsDigest;
    private TextView mNewsTime;
    public ViewHolder(View itemView) {
      super(itemView);
      mImageView= (ImageView) itemView.findViewById(R.id.iv_news_summary_photo);
      mNewsTitle= (TextView) itemView.findViewById(R.id.tv_news_summary_title);
      mNewsDigest= (TextView) itemView.findViewById(R.id.tv_news_summary_digest);
      mNewsTime= (TextView) itemView.findViewById(R.id.tv_news_summary_ptime);
    }

  }
}
