package com.biz.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.biz.R;
import com.biz.entry.ChannelEntry;
import com.biz.fragment.ImageFragment;
import com.base.controller.XBaseAppCompatActivity;


/**
 *
 */
public class RecyclerViewActivity extends XBaseAppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        setContentView(R.layout.activity_recyclerview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) this.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     *
     */
    public class MyAdapter extends FragmentStatePagerAdapter {

        public ChannelEntry[] mPagers = new ChannelEntry[]{new ChannelEntry("1", "ヒトツ"), new ChannelEntry("2", "フタツ"), new ChannelEntry("2", "ミツ")};

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(position);
            //return new ImageFragment(position);
        }

        @Override
        public int getCount() {
            return mPagers.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPagers[position].getName();
        }
    }
}
