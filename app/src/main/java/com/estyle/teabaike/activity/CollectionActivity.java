package com.estyle.teabaike.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.estyle.teabaike.R;
import com.estyle.teabaike.adapter.CollectionAdapter;
import com.estyle.teabaike.application.MyApplication;
import com.estyle.teabaike.bean.CollectionBean;
import com.estyle.teabaike.bean.CollectionBeanDao;
import com.estyle.teabaike.databinding.ActivityCollectionBinding;

import java.util.List;
import java.util.Locale;

public class CollectionActivity extends AppCompatActivity implements CollectionAdapter.OnItemClickListener, CollectionAdapter.OnItemLongClickListener, CollectionAdapter.OnCheckedAllItemListener {

    private ActivityCollectionBinding binding;

    private CollectionAdapter adapter;

    // 删除功能是否可用
    private boolean isDeleteEnabled;

    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);

        initView();
        initData();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new CollectionAdapter(this);
        adapter.setEmptyView(binding.emptyView);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        adapter.setOnCheckedAllItemListener(this);
        binding.setAdapter(adapter);

        snackbar = Snackbar.make(binding.toolbar, null, Snackbar.LENGTH_LONG)
                .setAction(R.string.revoke, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.restoreTempItem();
                    }
                })
                .addCallback(snackBarCallback);
    }

    private void initData() {
        List<CollectionBean> collectionList = ((MyApplication) getApplication()).getDaoSession()
                .getCollectionBeanDao()
                .queryBuilder()
                .orderDesc(CollectionBeanDao.Properties.CurrentTimeMillis)
                .build()
                .list();
        adapter.addDatas(collectionList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isDeleteEnabled) {
                    setDeleteEnabled(false);
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        if (!isDeleteEnabled) {
            Intent intent = new Intent(this, ContentActivity.class);
            intent.putExtra("id", adapter.getItemId(position));
            startActivity(intent);
        } else {
            adapter.invertItemStateAtPosition(position);
        }
    }

    @Override
    public void onItemLongClick(int position) {
        if (!isDeleteEnabled) {
            adapter.invertItemStateAtPosition(position);
            setDeleteEnabled(true);
        }
    }

    // 全选/取消全选按钮
    public void checkAll(View view) {
        Button deleteBtn = (Button) view;
        switch (deleteBtn.getText().toString()) {
            case "全选":
                adapter.setIsCheckedAllItem(true);
                break;
            case "取消":
                adapter.setIsCheckedAllItem(false);
                break;
        }
    }

    // 删除选中数据
    public void deleteItem(View view) {
        int count = adapter.deleteCheckedItem();
        setDeleteEnabled(false);
        snackbar.setText(String.format(Locale.getDefault(), getResources().getString(R.string.delete_successful), count))
                .show();
    }

    // 设置是否可删除
    private void setDeleteEnabled(boolean isDeleteEnabled) {
        this.isDeleteEnabled = isDeleteEnabled;
        adapter.setDeleteBoxVisibility(isDeleteEnabled);
        int visibility = isDeleteEnabled ? View.VISIBLE : View.INVISIBLE;
        binding.deleteBtn.setVisibility(visibility);
        binding.checkAllBtn.setVisibility(visibility);
    }

    @Override
    public void onBackPressed() {
        if (isDeleteEnabled) {
            setDeleteEnabled(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedAllItem() {
        binding.checkAllBtn.setText(R.string.uncheck_all);
    }

    @Override
    public void onUncheckedAllItem() {
        binding.checkAllBtn.setText(R.string.check_all);
    }

    // Snackbar消失回调
    private Snackbar.Callback snackBarCallback = new Snackbar.Callback() {
        @Override
        public void onDismissed(Snackbar transientBottomBar, int event) {
            super.onDismissed(transientBottomBar, event);
            adapter.deleteDataInDB();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        snackbar.removeCallback(snackBarCallback);
    }
}
