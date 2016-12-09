package com.river.image.module.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.river.image.R;
import com.river.image.bean.NewsBean;
import zhou.widget.RichText;

public class NewsDetailActivity extends AppCompatActivity {
  protected Toolbar mToolbar;
  protected FloatingActionButton mFloatingActionButton;
  protected TextView mTitle;
  protected TextView mFrom;
  protected RichText mRichText;
  private NewsBean.ShowapiResBodyBean.PageBean.ContentBean mContentBean;
  private ImageView mImageView;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_news_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });
    Intent intent=getIntent();
    mContentBean=
        (NewsBean.ShowapiResBodyBean.PageBean.ContentBean) intent.getSerializableExtra("news_content");
    initView();
    initData();
  }

  private void initView() {
    mImageView = (ImageView) findViewById(R.id.iv_news_detail_image);
    mRichText = (RichText) findViewById(R.id.tv_news_detail_body);
    mTitle= (TextView) findViewById(R.id.tv_news_detail_title);
    mFrom= (TextView) findViewById(R.id.tv_news_detail_from);
  }

  private void initData(){
    if(mContentBean!=null){
      if(!TextUtils.isEmpty(mContentBean.content)){
        mRichText.setText(mContentBean.content);
      }
    }


    if(mContentBean.imageurls.size()==0){
      mImageView.setImageResource(R.drawable.ic_header);
    }else{
      Glide.with(NewsDetailActivity.this).load(mContentBean.imageurls.get(0).url).placeholder(R.drawable.ic_loading).error(R.drawable.ic_fail).diskCacheStrategy(
          DiskCacheStrategy.SOURCE).into(mImageView);
    }
    mTitle.setText(mContentBean.title);

    mFrom.setText(getString(R.string.from,mContentBean.source,mContentBean.pubDate));
  }

}
