package com.biz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shsunframework.app.BaseActivity;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class CZSZMainActivity extends BaseActivity {

    private static final String TAG = "CZSZMainActivity";

    // 图片资源id
    private static int[] theViewPagerImageIds = new int[]{R.drawable.item01, R.drawable.item02, R.drawable.item03/*, R.drawable.item04,
                R.drawable.item05, R.drawable.item06, R.drawable.item07, R.drawable.item08*/};


    private ViewPager mViewPager;
    private List<View> mVPImageViews = new ArrayList<View>();
    CirclePageIndicator mIndicator;

    private Button mGoHomePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {
        //
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
                //Intent intent = new Intent(CZSZMainActivity.this, VitamioVideoPlayerActivity.class);
                //Intent intent = new Intent(CZSZMainActivity.this, SystemVideoPlayerActivity.class);
                CZSZMainActivity.this.startActivity(intent);
            }
        });

        //
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        final float density = getResources().getDisplayMetrics().density;
        mIndicator.setBackgroundColor(0xFFCCCCCC);
        mIndicator.setRadius(10 * density);
        mIndicator.setPageColor(0x880000FF);
        mIndicator.setFillColor(0xFF888888);
        mIndicator.setStrokeColor(0xFF000000);
        mIndicator.setStrokeWidth(2 * density);
        mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageScrolled-i:"+position+","+positionOffset+","+positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected-i:"+position);
                int idx = position % mVPImageViews.size();
                if (idx == mVPImageViews.size() - 1) {
                    CZSZMainActivity.this.mGoHomePageButton.setVisibility(View.VISIBLE);
                } else {
                    CZSZMainActivity.this.mGoHomePageButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged-i:"+state);
            }
        });
    }

    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {
        //
        for (int i = 0; i < theViewPagerImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(theViewPagerImageIds[i]);
            mVPImageViews.add(imageView);
        }

        mViewPager.setAdapter(new ViewPagerAdapter(mVPImageViews));

        mIndicator.setViewPager(mViewPager);
    }
}
