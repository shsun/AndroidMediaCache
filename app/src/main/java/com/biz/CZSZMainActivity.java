package com.biz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.base.net.XUrlConfigManager;
import com.biz.adapter.WelcomeViewPagerAdapter;
import com.base.controller.XBaseActivity;

import com.base.eventbus.XBaseEvent;
import com.viewpagerindicator.CirclePageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class CZSZMainActivity extends XBaseActivity {

    private static final String TAG = "CZSZMainActivity";

    // 图片资源id
    private static int[] theViewPagerImageIds = new int[] { R.drawable.item01, R.drawable.item02,
            R.drawable.item03/*
                              * , R.drawable.item04, R.drawable.item05, R.drawable.item06, R.drawable.item07, R.drawable.item08
                              */ };

    private ViewPager mViewPager;
    private List<View> mViewPageItems = new ArrayList<View>();
    CirclePageIndicator mPageIndicator;

    private WelcomeViewPagerAdapter mViewPagerAdapter;

    private Button mGoHomePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XUrlConfigManager.findURL(this, "login", this.getApplication().getResources().getXml(R.xml.url));
    }

    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {
    }

    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mGoHomePageButton = (Button) this.findViewById(R.id.btn_go_homepage);
        this.mGoHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(CZSZMainActivity.this, RecyclerViewActivity.class);
                Intent intent = new Intent(CZSZMainActivity.this, com.biz.activity.HomePageActivity.class);
                // Intent intent = new Intent(CZSZMainActivity.this, XVitamioVideoPlayerActivity.class);
                // Intent intent = new Intent(CZSZMainActivity.this, XSystemVideoPlayerActivity.class);
                CZSZMainActivity.this.startActivity(intent);
            }
        });

        //
        mPageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        final float density = getResources().getDisplayMetrics().density;
        mPageIndicator.setBackgroundColor(0xFFCCCCCC);
        mPageIndicator.setRadius(10 * density);
        mPageIndicator.setPageColor(0x880000FF);
        mPageIndicator.setFillColor(0xFF888888);
        mPageIndicator.setStrokeColor(0xFF000000);
        mPageIndicator.setStrokeWidth(2 * density);
        mPageIndicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, "onPageScrolled-i:" + position + "," + positionOffset + "," + positionOffsetPixels);
                EventBus.getDefault().post(new XBaseEvent<String>("go"));
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected-i:" + position);
                int idx = position % mViewPageItems.size();
                if (idx == mViewPageItems.size() - 1) {
                    CZSZMainActivity.this.mGoHomePageButton.setVisibility(View.VISIBLE);
                } else {
                    CZSZMainActivity.this.mGoHomePageButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG, "onPageScrollStateChanged-i:" + state);
            }
        });
    }

    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {
        // data
        for (int i = 0; i < theViewPagerImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(theViewPagerImageIds[i]);
            this.mViewPageItems.add(imageView);
        }
        this.mViewPagerAdapter = new WelcomeViewPagerAdapter(mViewPageItems);

        // notify
        mViewPager.setAdapter(this.mViewPagerAdapter);
        //
        mPageIndicator.setViewPager(mViewPager);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 0, sticky = true)
    public void handleEvent(XBaseEvent<String> event) {
        Log.d(TAG, event.getData());
    }
}
