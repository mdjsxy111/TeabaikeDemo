package com.estyle.teabaike.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.estyle.teabaike.R;
import com.estyle.teabaike.adapter.MainAdapter;
import com.estyle.teabaike.application.MyApplication;
import com.estyle.teabaike.bean.MainBean;
import com.estyle.teabaike.callback.SearchHttpService;
import com.estyle.teabaike.databinding.ActivitySearchBinding;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {

    private ActivitySearchBinding binding;

    private MainAdapter adapter;

    private int page = 1;
    private String keyword;
    private boolean isRefresh;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        initView();
        initData();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView searchRecyclerView = binding.searchPullToRefresh.getRefreshableView();
        binding.searchPullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        binding.searchPullToRefresh.setOnRefreshListener(this);

        adapter = new MainAdapter(this);
        adapter.setEmptyView(binding.emptyView);
        adapter.setOnItemClickListener(this);
        searchRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        keyword = getIntent().getStringExtra("keyword");
        getSupportActionBar().setTitle(keyword);

        loadData(page, false);
    }

    // 加载网络数据
    private void loadData(int page, boolean isRefresh) {
        this.isRefresh = isRefresh;
        subscription = ((MyApplication) getApplication()).getRetrofit()
                .create(SearchHttpService.class)
                .getObservable(keyword, page)
                .subscribeOn(Schedulers.io())
                .map(func)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext);
    }

    // Databinding和Java8 Lambda冲突
    private Func1<MainBean, List<MainBean.DataBean>> func = new Func1<MainBean, List<MainBean.DataBean>>() {
        @Override
        public List<MainBean.DataBean> call(MainBean mainBean) {
            return mainBean.getData();
        }
    };

    // Databinding和Java8 Lambda冲突
    private Action1<List<MainBean.DataBean>> onNext = new Action1<List<MainBean.DataBean>>() {
        @Override
        public void call(List<MainBean.DataBean> datas) {
            if (isRefresh) {
                adapter.refreshDatas(datas);
            } else {
                adapter.addDatas(datas);
            }
            binding.searchPullToRefresh.onRefreshComplete();
        }
    };

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("id", adapter.getItemId(position));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        loadData(page = 1, true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        loadData(++page, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
