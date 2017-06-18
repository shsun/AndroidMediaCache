package com.base.adapter.abslistview;

/**
 * @param <T>
 * @author shsun
 */
public interface MultiItemTypeSupport<T> {
    /**
     *
     * @param position
     * @param t
     * @return
     */
    int getLayoutId(int position, T t);

    /**
     *
     * @return
     */
    int getViewTypeCount();

    /**
     *
     * @param position
     * @param t
     * @return
     */
    int getItemViewType(int position, T t);
}