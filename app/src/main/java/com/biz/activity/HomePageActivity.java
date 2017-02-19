package com.biz.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.biz.R;
import com.biz.entry.ChannelEntry;
import com.biz.fragment.ActivityFragment;
import com.biz.fragment.FindFragment;
import com.biz.fragment.NewestFragment;
import com.biz.fragment.MyVideoFragment;
import com.shsunframework.app.BaseFragmentActivity;

/**
 * Created by shsun on 17/1/9.
 */
public class HomePageActivity extends BaseFragmentActivity {


    public static final String CZSZ_BUNDLE_KEY_CHANNEL_INFO = "CZSZ_BUNDLE_KEY_CHANNEL_INFO";
    public static final String CZSZ_BUNDLE_KEY_TEST = "CZSZ_BUNDLE_KEY_TEST";


    private List<ChannelEntry> mChannelEntries = new ArrayList<ChannelEntry>();





    private Fragment[] fragments = {new NewestFragment(), new ActivityFragment(),
            new MyVideoFragment(), new FindFragment(), new NewestFragment(), new NewestFragment(), new NewestFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        setContentView(R.layout.activity_homepage);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        //
        String[] titles = getResources().getStringArray(R.array.tab_titles);
        for (int i = 0; i < titles.length; i++) {
            mChannelEntries.add(new ChannelEntry("" + i, titles[i]));
        }

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 方案二：页面选中时才去加载数据
                // BaseFragment fragment = fragments[position];
                // fragment.initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {

    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        getApplication().onTerminate();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        Toast.makeText(HomePageActivity.this, "吓死宝宝了, 还好不是真退出！！！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确认退出?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("确认", listener);
        builder.setNegativeButton("取消", listener);
        builder.create().show();
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        // FragmentPagerAdapter与FragmentStatePagerAdapter区别
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments[position];
            Bundle bundle = new Bundle();
            bundle.putString("str", "fuck you");

            bundle.putParcelable(CZSZ_BUNDLE_KEY_CHANNEL_INFO, mChannelEntries.get(position));
            bundle.putString(CZSZ_BUNDLE_KEY_TEST, "what_fuck_is_going_on");

            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mChannelEntries.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mChannelEntries != null) {
                return mChannelEntries.get(position).getName();
            }
            return super.getPageTitle(position);
        }

    }
}