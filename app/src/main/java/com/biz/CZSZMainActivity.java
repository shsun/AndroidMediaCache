package com.biz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shsunframework.app.BaseActivity;

import com.shsunframework.app.VitamioVideoPlayerActivity;

public class CZSZMainActivity extends BaseActivity implements OnPageChangeListener {

    private static final String TAG = "CZSZMainActivity";

    // ViewPager
    private ViewPager mViewPager;

    // indicator
    // 装点点的ImageView数组
    private ImageView[] mIndicatorImgViews;

    // 装ImageView数组
    private ImageView[] mVPImageViews;

    // 图片资源id
    private int[] mVPImgIds;

    private Button mGoHomePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public final class WelcomePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // do not destory any item
            // FIXME
            // ((ViewPager) container).removeView(mVPImageViews[position % mVPImageViews.length]);
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            int idx = position % mVPImageViews.length;
            try {
                ((ViewPager) container).addView(mVPImageViews[idx], 0);
            } catch (Exception e) {
                //handler something
            }
            return mVPImageViews[idx];
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        int idx = arg0 % mVPImageViews.length;

        updateVPindicator(idx);

        if (idx == mVPImageViews.length - 1) {
            this.mGoHomePageButton.setVisibility(View.VISIBLE);
        } else {
            this.mGoHomePageButton.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems the index of indicator
     */
    private void updateVPindicator(int selectItems) {
        for (int i = 0; i < mIndicatorImgViews.length; i++) {
            if (i == selectItems) {
                mIndicatorImgViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                mIndicatorImgViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }


    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {
        //
    }

    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        this.mGoHomePageButton = (Button) this.findViewById(R.id.btn_go_homepage);
        this.mGoHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(CZSZMainActivity.this, RecyclerViewActivity.class);

                //Intent intent = new Intent(CZSZMainActivity.this, com.biz.activity.HomePageActivity.class);

                Intent intent = new Intent(CZSZMainActivity.this, VitamioVideoPlayerActivity.class);

                CZSZMainActivity.this.startActivity(intent);
            }
        });

        //载入图片资源ID
        mVPImgIds = new int[]{R.drawable.item01, R.drawable.item02, R.drawable.item03/*, R.drawable.item04,
                R.drawable.item05, R.drawable.item06, R.drawable.item07, R.drawable.item08*/};


        // 将点点加入到ViewGroup中
        mIndicatorImgViews = new ImageView[mVPImgIds.length];
        for (int i = 0; i < mIndicatorImgViews.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(10, 10));
            mIndicatorImgViews[i] = imageView;
            if (i == 0) {
                mIndicatorImgViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                mIndicatorImgViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);
        }

        // 将图片装载到数组中
        mVPImageViews = new ImageView[mVPImgIds.length];
        for (int i = 0; i < mVPImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mVPImageViews[i] = imageView;
            imageView.setBackgroundResource(mVPImgIds[i]);
        }

        // 设置Adapter
        mViewPager.setAdapter(new WelcomePagerAdapter());
        // 设置监听，主要是设置点点的背景
        mViewPager.setOnPageChangeListener(this);
        // 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        mViewPager.setCurrentItem((mVPImageViews.length) * 100);
    }

    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {
    }
}
