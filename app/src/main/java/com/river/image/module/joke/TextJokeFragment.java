package com.river.image.module.joke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.BindView;
import com.river.app.expandablelayout.library.ExpandableLayoutListView;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.bean.JokeBean;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.presenter.IJokeListPresenter;
import com.river.image.module.joke.presenter.IJokeListPresenterImpl;
import com.river.image.module.joke.view.IJokeListView;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class TextJokeFragment extends BaseFragment<IJokeListPresenter> implements IJokeListView {
  private String mType;
  private int mPosition;
  private static final String JOKE_TYPE = "joke_type";
  private static final String POSITION = "position";
  @BindView(R.id.listview) ExpandableLayoutListView listView;
  private List<JokeBean.ShowapiResBodyBean.ContentlistBean> mContentlist;
  @Override protected int getLayoutId() {
    return  R.layout.fragment_text_joke;
  }


  @Override protected void initData() {
    mPresenter = new IJokeListPresenterImpl(this);
    mPresenter.startLoadData(ApiConfig.TEXT_JOKE, "20", "1", ApiConfig.SHOWAPI_APPID, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void updateJokeList(JokeBean jokeBean) {
    mContentlist = jokeBean.showapi_res_body.contentlist;
    listView.setAdapter(new JokeAdapter(getActivity(),R.layout.item_jokes_list,mContentlist));
  }

  class JokeAdapter extends ArrayAdapter<JokeBean.ShowapiResBodyBean.ContentlistBean> {
    private int resource;
    LayoutInflater inflater;
    public JokeAdapter(Context context, int resource, List<JokeBean.ShowapiResBodyBean.ContentlistBean> objects) {
      super(context, resource, objects);
      this.resource=resource;
      inflater = LayoutInflater.from(context);
    }

    @NonNull @Override public View getView(int position, View convertView, ViewGroup parent) {
      JokeBean.ShowapiResBodyBean.ContentlistBean jokeBean=getItem(position);
      ViewHolder viewholder=null;
      if(convertView==null){
        viewholder=new  ViewHolder();
        convertView=inflater.inflate(resource,null,true);
        viewholder.tvHeader= (TextView) convertView.findViewById(R.id.header_text);
        viewholder.tvTitle= (TextView) convertView.findViewById(R.id.tv_news_detail_title);
        viewholder.tvFrom= (TextView) convertView.findViewById(R.id.tv_news_detail_from);
        viewholder.tvBody= (TextView) convertView.findViewById(R.id.tv_news_detail_body);
        convertView.setTag(viewholder);
      }else{
        viewholder= (ViewHolder) convertView.getTag();
      }

      viewholder.tvHeader.setText(jokeBean.title);
      viewholder.tvTitle.setText(jokeBean.title);
      viewholder.tvFrom.setText(jokeBean.ct);
      String p_Str=jokeBean.text;
      p_Str=p_Str.replace("<p>","");
      p_Str=p_Str.replace("</p>","");
      viewholder.tvBody.setText(p_Str);
      return convertView;
    }
    class ViewHolder{
      TextView tvHeader,tvTitle,tvFrom,tvBody;
    }
  }
}
