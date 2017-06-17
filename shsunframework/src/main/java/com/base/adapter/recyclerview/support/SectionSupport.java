package com.base.adapter.recyclerview.support;

/**
 *
 */
public interface SectionSupport<T> {
    /**
     * @return
     */
    public int sectionHeaderLayoutId();

    /**
     * @return
     */
    public int sectionTitleTextViewId();

    /**
     * @param t
     * @return
     */
    public String getTitle(T t);

}