package com.river.image.module.joke;

import android.content.Context;
import android.os.Bundle;
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
import com.river.image.bean.TextJokeBean;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.presenter.IJokeListPresenter;
import com.river.image.module.joke.presenter.IJokeListPresenterImpl;
import com.river.image.module.joke.view.IJokeListView;
import com.socks.library.KLog;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
//
public class JokeListFragment extends BaseFragment<IJokeListPresenter> implements IJokeListView {
  private String mType;
  private int mPosition;
  private static final String JOKE_TYPE = "joke_type";
  private static final String POSITION = "position";
  @BindView(R.id.listview) ExpandableLayoutListView listView;
  private List<TextJokeBean.ShowapiResBodyBean.ContentlistBean> mContentlist;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      mType = getArguments().getString(JOKE_TYPE);
       mPosition = getArguments().getInt(POSITION);
      KLog.d("TAG","position-->"+mPosition);

    }
  }

  public static JokeListFragment newInstance(String type,int position) {
    JokeListFragment fragment = new JokeListFragment();
    Bundle bundle = new Bundle();
    bundle.putString(JOKE_TYPE, type);
    bundle.putInt(POSITION, position);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override protected int getLayoutId() {
    return mLayout;
  }

  @Override protected void initData() {
    mPresenter = new IJokeListPresenterImpl(this);
    mPresenter.startLoadData(mType, "20", "1", ApiConfig.SHOWAPI_APPID, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void updateJokeList(TextJokeBean jokeBean) {
    mContentlist = jokeBean.showapi_res_body.contentlist;
    initNewsList();
  }

  public void initNewsList() {

    listView.setAdapter(new JokeAdapter(getActivity(),R.layout.item_jokes_list,mContentlist));
  }

  class JokeAdapter extends ArrayAdapter<TextJokeBean.ShowapiResBodyBean.ContentlistBean> {
    private int resource;
    LayoutInflater inflater;
    public JokeAdapter(Context context, int resource, List<TextJokeBean.ShowapiResBodyBean.ContentlistBean> objects) {
      super(context, resource, objects);
      this.resource=resource;
       inflater = LayoutInflater.from(context);
    }

    @NonNull @Override public View getView(int position, View convertView, ViewGroup parent) {
      TextJokeBean.ShowapiResBodyBean.ContentlistBean jokeBean=getItem(position);
          ViewHolder viewholder=null;
      if(convertView==null){
        viewholder=new ViewHolder();
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
      viewholder.tvBody.setText(jokeBean.text);
      return convertView;
    }
    class ViewHolder{
      TextView tvHeader,tvTitle,tvFrom,tvBody;
    }
  }
}
