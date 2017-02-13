package com.estyle.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;


public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    private LinearLayoutManager layoutManager;

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        int lastItemPosition = getRefreshableView().getAdapter().getItemCount() - 1;
        return layoutManager.findLastCompletelyVisibleItemPosition() == lastItemPosition;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
    }
}
