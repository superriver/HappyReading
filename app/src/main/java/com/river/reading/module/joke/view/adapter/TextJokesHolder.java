package com.river.reading.module.joke.view.adapter;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.river.image.R;
import com.river.reading.bean.JokeBean;

/**
 * Created by Administrator on 2017/2/8.
 */

public class TextJokesHolder extends BaseViewHolder<JokeBean.ShowapiResBodyBean.ContentlistBean> {
  private TextView tvHeader,tvTitle,tvFrom,tvBody;
  public TextJokesHolder(ViewGroup itemView) {
    super(itemView, R.layout.item_jokes_list);
    tvHeader= $(R.id.header_text);
    tvTitle= $(R.id.tv_news_detail_title);
    tvFrom= $(R.id.tv_news_detail_from);
    tvBody= $(R.id.tv_news_detail_body);
  }

  @Override public void setData(JokeBean.ShowapiResBodyBean.ContentlistBean data) {
    super.setData(data);
    tvHeader.setText(data.title);
    tvTitle.setText(data.title);
    tvFrom.setText(data.ct);
    String p_Str=data.text;
    p_Str=p_Str.replace("<p>","");
    p_Str=p_Str.replace("</p>","");
    tvBody.setText(p_Str);
  }
}
