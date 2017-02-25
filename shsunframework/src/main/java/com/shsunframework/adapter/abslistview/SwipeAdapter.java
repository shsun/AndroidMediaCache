package com.shsunframework.adapter.abslistview;

import android.content.Context;

import java.util.List;


/**
 * @param <T>
 */
public abstract class SwipeAdapter<T> extends BaseAbListViewAdapter<T> {
    public SwipeAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);

    }

    public boolean getSwipEnableByPosition(int position) {
        return true;
    }

}
