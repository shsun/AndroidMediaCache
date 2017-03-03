package com.biz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.biz.entry.ChannelEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shsun on 17/3/3.
 */

public class FragmentAdapter extends FragmentPagerAdapter {


    public static final String CZSZ_BUNDLE_KEY_CHANNEL_INFO = "CZSZ_BUNDLE_KEY_CHANNEL_INFO";
    public static final String CZSZ_BUNDLE_KEY_TEST = "CZSZ_BUNDLE_KEY_TEST";

    private List<Fragment> fragments;
    private List<ChannelEntry> mChannelEntries = new ArrayList<ChannelEntry>();

    public FragmentAdapter(FragmentManager fm, List<Fragment> f, List<ChannelEntry> list) {
        super(fm);
        this.mChannelEntries = list;
        this.fragments = f;
        //
        String[] title = new String[this.mChannelEntries.size()];
        for (int i = 0; i < title.length; i++) {
            title[i] = this.mChannelEntries.get(i).getName();
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("str", "fuck you");
        bundle.putParcelable(CZSZ_BUNDLE_KEY_CHANNEL_INFO, mChannelEntries.get(position));
        bundle.putString(CZSZ_BUNDLE_KEY_TEST, "what_fuck_is_going_on");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mChannelEntries != null) {
            return mChannelEntries.get(position).getName();
        }
        return super.getPageTitle(position);
    }
}