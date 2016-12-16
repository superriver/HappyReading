package com.river.image.module.news.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.river.image.R;
import com.river.image.annotation.ActivityFragmentInject;
import com.river.image.base.BaseActivity;
import com.river.image.bean.NewsBean;
import zhou.widget.RichText;

@ActivityFragmentInject(contentViewId = R.layout.content_news_detail)
public class NewsDetailActivity extends BaseActivity {
  @BindView(R.id.toolbar)  Toolbar mToolbar;
  @BindView(R.id.fab)  FloatingActionButton fab;
  @BindView(R.id.tv_news_detail_title)  TextView mTitle;
  @BindView(R.id.tv_news_detail_from)  TextView mFrom;
  @BindView(R.id.tv_news_detail_body)  RichText mRichText;
  @BindView(R.id.iv_news_detail_image)  ImageView mImageView;
  private NewsBean.ShowapiResBodyBean.PageBean.ContentBean mContentBean;

  @Override protected int getFragmentContentId() {
    return 0;
  }

  @Override protected void initView() {
    setSupportActionBar(mToolbar);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });

    initData();
  }

  private void initData() {
    Intent intent = getIntent();
    mContentBean = (NewsBean.ShowapiResBodyBean.PageBean.ContentBean) intent.getSerializableExtra(
        "news_content");
    if (mContentBean != null) {
      if (!TextUtils.isEmpty(mContentBean.content)) {
        mRichText.setText(mContentBean.content);
      }
    }
    if (mContentBean.imageurls.size() == 0) {
      //mImageView.setImageResource(R.drawable.ic_header);
    } else {
      Glide.with(NewsDetailActivity.this)
          .load(mContentBean.imageurls.get(0).url)
          .placeholder(R.drawable.ic_loading)
          .error(R.drawable.ic_fail)
          .diskCacheStrategy(DiskCacheStrategy.SOURCE)
          .into(mImageView);
    }
    mTitle.setText(mContentBean.title);

    mFrom.setText(getString(R.string.from, mContentBean.source, mContentBean.pubDate));
  }
}
