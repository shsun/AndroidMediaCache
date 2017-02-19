package com.biz.homepage;

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
import com.biz.homepage.fragment.ActivityFragment;
import com.biz.homepage.fragment.AmuseFragment;
import com.biz.homepage.fragment.FindFragment;
import com.biz.homepage.fragment.NewestFragment;
import com.danikula.videocache.demo.GalleryVideoFragment;
import com.danikula.videocache.demo.MyVideoFragment;
import com.danikula.videocache.demo.Video;
import com.danikula.videocache.demo.VideoFragment;
import com.shsunframework.app.BaseFragment;
import com.shsunframework.app.BaseFragmentActivity;

/**
 * Created by shsun on 17/1/9.
 */
public class HomePageActivity extends BaseFragmentActivity {
    private String[] mTabTitles = new String[]{};


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

        mTabTitles = getResources().getStringArray(R.array.tab_titles);

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
                        // System.exit(0);
                        // android.os.Process.killProcess(android.os.Process.myPid());

                        getApplication().onTerminate();

                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        Toast.makeText(com.biz.homepage.HomePageActivity.this, "吓死宝宝了, 还好不是真退出！！！", Toast.LENGTH_SHORT).show();
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
            return fragments[position];
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTabTitles != null) {
                return mTabTitles[position];
            }
            return super.getPageTitle(position);
        }

    }
}