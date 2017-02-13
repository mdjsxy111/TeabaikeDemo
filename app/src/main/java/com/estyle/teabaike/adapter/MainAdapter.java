package com.estyle.teabaike.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estyle.teabaike.R;
import com.estyle.teabaike.bean.MainBean;
import com.estyle.teabaike.databinding.ItemMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<MainBean.DataBean> datas;

    private List<View> headerViewList;
    private View emptyView;

    private OnItemClickListener onItemClickListener;

    public MainAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        headerViewList = new ArrayList<>();
    }

    // 刷新数据
    public void refreshDatas(List<MainBean.DataBean> datas) {
        this.datas.clear();
        addDatas(datas);

    }

    // 添加数据
    public void addDatas(List<MainBean.DataBean> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    // 添加头视图
    public void addHeaderView(View headerView) {
        headerViewList.add(headerView);
    }

    // 设置空视图
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == headerViewList.size()) {
            ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main, parent, false);
            View itemView = binding.getRoot();
            initItemViewListener(itemView);
            holder = new ItemViewHolder(itemView);
            ((ItemViewHolder) holder).setBinding(binding);
        } else {
            View headerView = headerViewList.get(viewType);
            holder = new HeaderViewHolder(headerView);
        }
        return holder;
    }

    private void initItemViewListener(View itemView) {
        itemView.setOnClickListener(this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            MainBean.DataBean data = datas.get(position - headerViewList.size());
            ItemMainBinding binding = itemViewHolder.getBinding();
            binding.setBean(data);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = datas.size() + headerViewList.size();
        if (emptyView != null) {
            if (itemCount > 0) {
                emptyView.setVisibility(View.INVISIBLE);
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
        }
        return itemCount;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(datas.get(position - headerViewList.size()).getId());
    }

    @Override
    public int getItemViewType(int position) {
        if (headerViewList.size() > 0) {
            if (position < headerViewList.size()) {
                return position;
            } else {
                return headerViewList.size();
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View view) {
        int position;
        switch (view.getId()) {
            default:
                if (onItemClickListener != null) {
                    position = ((RecyclerView) view.getParent()).getChildLayoutPosition(view);
                    onItemClickListener.onItemClick(position);
                }
                break;
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemMainBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        public ItemMainBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemMainBinding binding) {
            this.binding = binding;
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static interface OnItemClickListener {
        public void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}