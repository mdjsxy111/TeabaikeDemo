package com.estyle.teabaike.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.estyle.teabaike.R;
import com.estyle.teabaike.adapter.WelcomePagerAdapter;
import com.estyle.teabaike.databinding.ActivityWelcomeBinding;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private int[] imgIds = new int[]{R.drawable.welcome1, R.drawable.welcome2};

    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        initView();
    }

    private void initView() {
        List<View> viewList = new ArrayList<>();
        View view;
        for (int i = 0; i < 2; i++) {
            view = new View(this);
            view.setBackgroundResource(imgIds[i]);
            viewList.add(view);
        }
        view = getLayoutInflater().inflate(R.layout.view_welcome3, null);
        Button mainBtn = (Button) view.findViewById(R.id.main_btn);
        mainBtn.setOnClickListener(this);
        viewList.add(view);

        WelcomePagerAdapter adapter = new WelcomePagerAdapter(viewList);
        binding.welcomeViewPager.setAdapter(adapter);
        binding.welcomeViewPager.addOnPageChangeListener(this);

        binding.pointView.setPointCount(viewList.size());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        binding.pointView.setSelectedPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.welcomeViewPager.removeOnPageChangeListener(this);
    }
}
