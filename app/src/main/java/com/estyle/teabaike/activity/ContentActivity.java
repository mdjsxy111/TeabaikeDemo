package com.estyle.teabaike.activity;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.estyle.teabaike.R;
import com.estyle.teabaike.application.MyApplication;
import com.estyle.teabaike.bean.CollectionBean;
import com.estyle.teabaike.bean.CollectionBeanDao;
import com.estyle.teabaike.bean.ContentBean;
import com.estyle.teabaike.callback.ContentHttpService;
import com.estyle.teabaike.databinding.ActivityContentBinding;

import org.greenrobot.greendao.query.Query;

import java.lang.reflect.Method;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ContentActivity extends AppCompatActivity {

    private ActivityContentBinding binding;

    private ContentBean.DataBean data;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content);

        initView();
        initData();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(0x3d, 0x9d, 0x01));
    }

    private void initData() {
        long id = getIntent().getLongExtra("id", 0);
        subscription = ((MyApplication) getApplication())
                .getRetrofit()
                .create(ContentHttpService.class)
                .getObservable(id)
                .subscribeOn(Schedulers.io())
                .flatMap(func)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    // Databinding和Java8 Lambda冲突
    private Func1<ContentBean, Observable<ContentBean.DataBean>> func = new Func1<ContentBean, Observable<ContentBean.DataBean>>() {
        @Override
        public Observable<ContentBean.DataBean> call(ContentBean contentBean) {
            return Observable.just(contentBean.getData());
        }
    };

    // Databinding和Java8 Lambda冲突
    private Action1<ContentBean.DataBean> onNext = new Action1<ContentBean.DataBean>() {
        @Override
        public void call(ContentBean.DataBean dataBean) {
            binding.setBean(dataBean);
            data = dataBean;
        }
    };

    // Databinding和Java8 Lambda冲突
    private Action1<Throwable> onError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Toast.makeText(ContentActivity.this, R.string.fail_connect, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);

        Class clazz = menu.getClass();
        Method setOptionalIconsVisibleMethod = null;
        try {
            setOptionalIconsVisibleMethod = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            setOptionalIconsVisibleMethod.setAccessible(true);
            setOptionalIconsVisibleMethod.invoke(menu, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_item:
                Toast.makeText(this, R.string.share_successful, Toast.LENGTH_SHORT).show();
                break;
            case R.id.collect_item:
                collect();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 收藏文章
    private void collect() {
        if (data != null) {
            CollectionBeanDao collectionDao = ((MyApplication) getApplication()).getDaoSession()
                    .getCollectionBeanDao();
            Query<CollectionBean> query = collectionDao.queryBuilder()
                    .where(CollectionBeanDao.Properties.Id.eq(data.getId()))
                    .build();
            if (query.unique() == null) {
                CollectionBean collection = new CollectionBean(Long.parseLong(data.getId())
                        , System.currentTimeMillis()
                        , data.getTitle()
                        , data.getSource()
                        , data.getCreate_time()
                        , data.getAuthor());
                collectionDao.insert(collection);
            }
            Toast.makeText(this, R.string.collect_successful, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
