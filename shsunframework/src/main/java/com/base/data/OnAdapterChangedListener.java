package com.base.data;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shsun on 17/2/25.
 */

public interface OnAdapterChangedListener<T> {

    void onItemAdd(ViewGroup parent, View view, T data, int position);

    void onItemChanged(ViewGroup parent, View view, T data, int position);

    void onItemInvalidate(ViewGroup parent, View view, T data, int position);

    void onInvalidateAll(ViewGroup parent, View view, T data, int position);

    void onItemRemove(ViewGroup parent, View view, T data, int position);

    void onItemRemoveAll(ViewGroup parent, View view, T data, int position);

    void onItemReplace(ViewGroup parent, View view, T data, int position);

    void onItemSort(ViewGroup parent, View view, T data, int position);

}
