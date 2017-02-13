package com.estyle.teabaike.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.estyle.teabaike.R;
import com.estyle.teabaike.adapter.MainPagerAdapter;
import com.estyle.teabaike.bean.ChannelBean;
import com.estyle.teabaike.constant.Url;
import com.estyle.teabaike.databinding.ActivityMainBinding;
import com.estyle.teabaike.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);

        toggle = new ActionBarDrawerToggle(this, binding.mainDrawerLayout, binding.toolbar, 0, 0);
        toggle.syncState();
        binding.mainDrawerLayout.addDrawerListener(toggle);

        List<ChannelBean> channelList = new ArrayList<>();
        for (int i = 0; i < Url.TYPES.length; i++) {
            channelList.add(new ChannelBean(Url.TITLES[i], MainFragment.newInstance(Url.TYPES[i])));
        }

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), channelList);
        binding.mainViewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.mainViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mainDrawerLayout.removeDrawerListener(toggle);
        toggle = null;
    }

    @Override
    public void onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            exitApp();
        }
    }

    // 当前点击返回键的时间

    private long currentBackPressedTime;

    private void exitApp() {
        if (System.currentTimeMillis() - currentBackPressedTime > 2000) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.exit_tip, Toast.LENGTH_SHORT).show();
        } else {
            try {
                super.onBackPressed();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
}
