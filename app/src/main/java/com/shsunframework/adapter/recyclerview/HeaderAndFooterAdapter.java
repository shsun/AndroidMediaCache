package com.shsunframework.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by shsun on 17/2/19.
 */
public abstract class HeaderAndFooterAdapter<H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    int headerSize;
    int footerSize;

    public int getFooterSize() {
        return footerSize;
    }

    public void setFooterSize(int footerSize) {
        this.footerSize = footerSize;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }
}