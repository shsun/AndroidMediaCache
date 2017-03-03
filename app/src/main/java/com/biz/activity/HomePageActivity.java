package com.biz.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.biz.R;
import com.biz.adapter.HomePageFragmentAdapter;
import com.biz.entry.ChannelEntry;
import com.biz.fragment.FindFragment;
import com.biz.fragment.ImageFragment;
import com.biz.fragment.MyVideoFragment;
import com.biz.fragment.NewestFragment;
import com.shsunframework.app.BaseFragment;
import com.shsunframework.app.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shsun on 17/1/9.
 */
public class HomePageActivity extends BaseFragmentActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private List<Fragment> allFragment = new ArrayList<Fragment>();

    private List<ChannelEntry> mChannelEntries = new ArrayList<ChannelEntry>();

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

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 方案二：页面选中时才去加载数据
                BaseFragment fragment = (BaseFragment)allFragment.get(position);
                // fragment.initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {
        //
        allFragment.add(new NewestFragment());
        allFragment.add(ImageFragment.newInstance(0));
        allFragment.add(ImageFragment.newInstance(1));
        allFragment.add(ImageFragment.newInstance(2));
        allFragment.add(new MyVideoFragment());
        allFragment.add(new FindFragment());
        allFragment.add(new NewestFragment());
        //
        String[] titles = getResources().getStringArray(R.array.tab_titles);
        for (int i = 0; i < titles.length; i++) {
            mChannelEntries.add(new ChannelEntry("" + i, titles[i]));
        }
        mViewPager.setAdapter(new HomePageFragmentAdapter(getSupportFragmentManager(), allFragment, mChannelEntries));

        mTabLayout.setupWithViewPager(mViewPager);
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
}