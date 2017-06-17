package com.base.adapter.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shsun on 17/3/2.
 */

public class BaseViewPagerAdapter extends PagerAdapter {

    protected List<View> mDatas;

    public BaseViewPagerAdapter(List<View> datas) {
        this.mDatas = (datas == null) ? new ArrayList<View>() : datas;
    }

    public void setDataProvider(List<View> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
