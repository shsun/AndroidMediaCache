package com.shsunframework.adapter.abslistview;

/**
 * @param <T>
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int position, T t);
}