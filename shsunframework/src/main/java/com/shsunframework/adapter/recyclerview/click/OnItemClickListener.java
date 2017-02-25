package com.shsunframework.adapter.recyclerview.click;

import android.view.View;
import android.view.ViewGroup;


/**
 *
 * @param <T>
 */
public interface OnItemClickListener<T> {

    void onItemClick(ViewGroup parent, View view, T data, int position);
}
