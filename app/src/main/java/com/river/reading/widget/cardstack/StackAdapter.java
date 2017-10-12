package com.river.reading.widget.cardstack;

import android.content.Context;
import android.view.LayoutInflater;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public abstract class StackAdapter<T> extends CardStackView.Adapter<CardStackView.ViewHolder> {

    protected final Context mContext;
    protected final LayoutInflater mInflater;
    protected List<T> mData;

    public StackAdapter(Context context,List<T> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data==null?new ArrayList():data;
    }

    //public void updateData(List<T> data) {
    //    this.setData(data);
    //    this.notifyDataSetChanged();
    //}

    public void setData(List<T> data) {
        //if (data != null) {
        //    this.mData.addAll(data);
        //}
        mData=data;
        notifyDataSetChanged();
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public void onBindViewHolder(CardStackView.ViewHolder holder, int position) {
        KLog.d("TAG","onBindViewHolder--position>"+position);
        T data = this.getItem(position);
        this.bindView(data, position, holder);
    }

    public abstract void bindView(T data, int position, CardStackView.ViewHolder holder);

    @Override
    public int getItemCount() {
        return null!=mData?mData.size():0;
    }

    public T getItem(int position) {
        return this.mData.get(position);
    }

}
