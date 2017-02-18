package com.biz.bakup;

import java.io.IOException;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

import android.support.v4.view.ViewPager;

import com.shsunframework.utils.MathUtils;
import com.biz.net.BMServer;

import com.biz.R;

/**
 *
 */
public class HomePageActivity extends AppCompatActivity {
    private static final String SP_NAME = "isAppLaunched";
    private static final String SP_KEY_IS_APP_LAUNCHED = "isAppLaunched";
    private static final String SP_KEY_PKG_VERSION_CODE = "versionCode";
    private static final String SP_KEY_PKG_VERSION_NAME = "versionName";

    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[] { "头条", "房产", "另一面", "女人",
            "财经", "数码", "情感", "科技" };


    private BMServer mServer = null;
    private int mBMServerStartCounter = 0;

    NewsTabPagerAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_activity_homepage);
        startBMServer();

        boolean isAppLaunched = false;
        try {
            SharedPreferences sp = this.getSharedPreferences(SP_NAME, 0);
            isAppLaunched = sp.getBoolean(SP_KEY_IS_APP_LAUNCHED, false);

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(SP_KEY_IS_APP_LAUNCHED, true);
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            editor.putInt(SP_KEY_PKG_VERSION_CODE, info.versionCode);
            editor.putInt(SP_KEY_PKG_VERSION_NAME, info.versionCode);


            // Unlike commit(), which writes its preferences out to persistent storage synchronously,
            // apply() commits its changes to the in-memory SharedPreferences immediately but starts
            // an asynchronous commit to disk and you won't be notified of any failures.
            // editor.commit();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply();
            } else {
                editor.commit();
            }
        } catch (Exception e) {
            isAppLaunched = false;
        }

        // ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        mPager = (ViewPager)findViewById(R.id.vp_news);
        mPager.setAdapter(adapter);



        // ViewPagerIndicator声明
        TabPageIndicator indicator = (TabPageIndicator) this
                .findViewById(R.id.vpi_indicator);
        indicator.setViewPager(mPager);// 绑定必须要在viewpager设置好数据适配器之后


// 如果我们要对ViewPager设置监听，用indicator设置就行了
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Toast.makeText(getApplicationContext(), TITLE[arg0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private void startBMServer() {
        try {
            if (mBMServerStartCounter < 10) {
                mBMServerStartCounter += 1;
                this.mServer = new BMServer(MathUtils.random(1000, 9999));
            } else {
                this.mServer = null;
            }
        } catch (IOException e) {
            startBMServer();
        }
    }


    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        System.exit(0);
                        // android.os.Process.killProcess(android.os.Process.myPid());
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
        builder.setPositiveButton("确认", dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }


    final class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putString("arg", TITLE[position]);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }

}
