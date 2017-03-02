package com.biz;

/**
 * Created by shsun on 17/3/2.
 */

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import com.shsunframework.adapter.viewpager.BaseViewPagerAdapter;

/**
 *
 */
public class WelcomeViewPagerAdapter extends BaseViewPagerAdapter {

    public WelcomeViewPagerAdapter(List<View> datas) {
        super(datas);
    }

    @Override
    public int getCount() {
        return this.mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(this.mDatas.get(position));
        return mDatas.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
