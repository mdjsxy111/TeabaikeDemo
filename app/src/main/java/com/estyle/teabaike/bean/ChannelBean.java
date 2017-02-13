package com.estyle.teabaike.bean;

import android.support.v4.app.Fragment;

public class ChannelBean {

    private String channel;
    private Fragment fragment;

    public ChannelBean(String channel, Fragment fragment) {
        this.channel = channel;
        this.fragment = fragment;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
