package com.estyle.teabaike.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.estyle.teabaike.R;
import com.estyle.teabaike.databinding.ActivitySuggestionBinding;


public class SuggestionActivity extends AppCompatActivity {

    private ActivitySuggestionBinding binding;

    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_suggestion);
        binding.setActivity(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // 提交按钮
    public void commit() {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
            Toast.makeText(this, "发表成功！", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // 接收标题
    public void setTitle(Editable s) {
        title = s.toString();
    }

    // 接收内容
    public void setContent(Editable s) {
        content = s.toString();
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
}
