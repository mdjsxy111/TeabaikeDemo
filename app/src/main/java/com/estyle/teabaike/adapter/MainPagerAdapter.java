package com.estyle.teabaike.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.estyle.teabaike.bean.ChannelBean;

import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<ChannelBean> channelList;

    public MainPagerAdapter(FragmentManager fm, List<ChannelBean> channelList) {
        super(fm);
        this.channelList = channelList;
    }

    @Override
    public Fragment getItem(int position) {
        return channelList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return channelList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return channelList.get(position).getChannel();
    }
}
