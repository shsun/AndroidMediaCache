package com.danikula.videocache.demo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.biz.R;

@EActivity(R.layout.activity_video_gallery)
public class VideoGalleryActivity extends FragmentActivity {

    @ViewById ViewPager viewPager;
    @ViewById CirclePageIndicator viewPagerIndicator;

    @AfterViews
    void afterViewInjected() {
        ViewsPagerAdapter viewsPagerAdapter = new ViewsPagerAdapter(this);
        viewPager.setAdapter(viewsPagerAdapter);
        viewPagerIndicator.setViewPager(viewPager);
    }

    private static final class ViewsPagerAdapter extends FragmentStatePagerAdapter {

        private final Context context;

        public ViewsPagerAdapter(FragmentActivity activity) {
            super(activity.getSupportFragmentManager());
            this.context = activity;
        }

        @Override
        public Fragment getItem(int position) {
            Video video = Video.values()[position];
            return GalleryVideoFragment.build(context, video);
        }

        @Override
        public int getCount() {
            return Video.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Video.values()[position].name();
        }
    }
}
