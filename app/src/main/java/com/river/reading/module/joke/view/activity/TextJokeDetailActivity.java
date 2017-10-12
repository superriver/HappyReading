package com.river.reading.module.joke.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import butterknife.BindView;
import com.river.image.R;
import com.river.reading.annotation.ActivityFragmentInject;
import com.river.reading.base.BaseActivity;
import com.river.reading.bean.JokeBean;
import com.river.reading.event.MessageEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhou.widget.RichText;

@ActivityFragmentInject(contentViewId = R.layout.text_joke_detail) public class TextJokeDetailActivity
    extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.tv_joke_detail_title) TextView mTitle;
  @BindView(R.id.tv_joke_detail_from) TextView mFrom;
  @BindView(R.id.tv_joke_detail_body) RichText mRichText;
  private JokeBean.ShowapiResBodyBean.ContentlistBean data;

  @Override protected int getFragmentContentId() {
    return 0;
  }

  @Override protected void initView() {
    EventBus.getDefault().register(TextJokeDetailActivity.this);

    setSupportActionBar(mToolbar);
    //fab.setOnClickListener(
    //    view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
    //        .setAction("Action", null)
    //        .show());
    initData();
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onMessageEvent(MessageEvent event) {
    if (event != null) {
      data = (JokeBean.ShowapiResBodyBean.ContentlistBean) event.getData();
    }
  }

  private void initData() {
    if (data != null) {
      if (!TextUtils.isEmpty(data.text)) {
        mRichText.setText(data.text);
      }
      mTitle.setText(data.title);
      mFrom.setText(data.ct);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
