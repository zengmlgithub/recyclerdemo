package com.sanyedu.recyclerviewdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sanyedu.recyclerviewdemo.R;
import com.sanyedu.recyclerviewdemo.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * 上拉加载更多
 * Created by yangle on 2017/10/12.
 */

public class LoadMoreWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataList;

    public LoadMoreWrapperAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    public LoadMoreWrapperAdapter(){

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recyclerview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvItem.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
