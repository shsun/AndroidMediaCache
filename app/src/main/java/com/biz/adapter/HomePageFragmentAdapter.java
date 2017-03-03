package com.biz.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.biz.entry.ChannelEntry;
import com.shsunframework.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shsun on 17/3/3.
 */

public class HomePageFragmentAdapter extends SectionsPagerAdapter {


    public static final String CZSZ_BUNDLE_KEY_CHANNEL_INFO = "CZSZ_BUNDLE_KEY_CHANNEL_INFO";
    public static final String CZSZ_BUNDLE_KEY_TEST = "CZSZ_BUNDLE_KEY_TEST";

    private List<ChannelEntry> mChannelEntries = new ArrayList<ChannelEntry>();

    public HomePageFragmentAdapter(FragmentManager fm, List<Fragment> f, List<ChannelEntry> list) {
        super(fm, f);

        this.mChannelEntries = list;

        String[] titles = new String[this.mChannelEntries.size()];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = this.mChannelEntries.get(i).getName();
        }
        this.mTitles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("str", "fuck you");
        bundle.putParcelable(CZSZ_BUNDLE_KEY_CHANNEL_INFO, mChannelEntries.get(position));
        bundle.putString(CZSZ_BUNDLE_KEY_TEST, "what_fuck_is_going_on");
        fragment.setArguments(bundle);
        return fragment;
    }
}