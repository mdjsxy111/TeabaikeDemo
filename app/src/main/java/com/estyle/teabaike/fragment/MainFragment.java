package com.estyle.teabaike.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estyle.teabaike.R;
import com.estyle.teabaike.activity.ContentActivity;
import com.estyle.teabaike.adapter.MainAdapter;
import com.estyle.teabaike.application.MyApplication;
import com.estyle.teabaike.bean.MainBean;
import com.estyle.teabaike.callback.MainHttpService;
import com.estyle.teabaike.databinding.FragmentMainBinding;
import com.estyle.teabaike.widget.ViewFactory;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainFragment extends Fragment implements MainAdapter.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {

    private FragmentMainBinding binding;

    private int type;
    private int page = 1;
    private Retrofit retrofit;
    private MainAdapter adapter;
    private ViewFactory viewFactory;
    private View rootView;
    private boolean isViewCreated;
    private boolean isRefresh;
    private Subscription subscription;

    public static MainFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getInt("type", 0);

        retrofit = ((MyApplication) getActivity().getApplication()).getRetrofit();

        adapter = new MainAdapter(getContext());
        adapter.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!isViewCreated) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
            rootView = binding.getRoot();
            adapter.setEmptyView(binding.emptyView);

            if (type == 0) {
                viewFactory = new ViewFactory(getContext());
                View headerView = viewFactory.newHeadlineHeaderView();
                adapter.addHeaderView(headerView);
            }

            binding.mainPullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
            binding.mainPullToRefresh.setOnRefreshListener(this);

            RecyclerView mainRecyclerView = binding.mainPullToRefresh.getRefreshableView();
            mainRecyclerView.setAdapter(adapter);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isViewCreated) {
            loadData(page, false);
            isViewCreated = true;
        }
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
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        intent.putExtra("id", adapter.getItemId(position));
        startActivity(intent);
    }

    // 加载网络数据
    private void loadData(int page, boolean isRefresh) {
        this.isRefresh = isRefresh;
        MainHttpService service = retrofit.create(MainHttpService.class);

        Observable<MainBean> observable;
        if (type == 0) {
            observable = service.getHeadlineObservable(page);
        } else {
            observable = service.getMainObservable(type, page);
        }

        subscription = observable.subscribeOn(Schedulers.io())
                .map(func)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
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
            binding.mainPullToRefresh.onRefreshComplete();
        }
    };

    // Databinding和Java8 Lambda冲突
    private Action1<Throwable> onError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            binding.emptyView.setText(R.string.fail_connect);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (type == 0) {
            viewFactory.onDestroy();
            viewFactory = null;
        }
        subscription.unsubscribe();
    }
}
