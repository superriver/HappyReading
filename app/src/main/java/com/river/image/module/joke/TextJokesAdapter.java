package com.river.image.module.joke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.river.image.R;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import java.util.List;
/**
 * Created by Administrator on 2017/2/8.
 */

public class TextJokesAdapter extends BaseAdapter {
    private int resource;
    private LayoutInflater inflater;
    private List<ContentlistBean> datas;
    public TextJokesAdapter(Context context, int resource, List<ContentlistBean> datas) {
      this.resource=resource;
      inflater = LayoutInflater.from(context);
      this.datas=datas;
    }

  /**
   * How many items are in the data set represented by this Adapter.
   *
   * @return Count of items.
   */
  @Override public int getCount() {
    return datas.size();
  }

  /**
   * Get the data item associated with the specified position in the data set.
   *
   * @param position Position of the item whose data we want within the adapter's
   * data set.
   * @return The data at the specified position.
   */
  @Override public ContentlistBean getItem(int position) {
    return datas.get(position);
  }

  /**
   * Get the row id associated with the specified position in the list.
   *
   * @param position The position of the item within the adapter's data set whose row id we want.
   * @return The id of the item at the specified position.
   */
  @Override public long getItemId(int position) {
    return 0;
  }

  @NonNull @Override public View getView(int position, View convertView, ViewGroup parent) {
      ContentlistBean jokeBean=getItem(position);
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

  //public void addAll(List<ContentlistBean> mContentlist){
  //  for(ContentlistBean contentlistBean:mContentlist) {
  //    datas.add(contentlistBean);
  //  }
  //}
}
