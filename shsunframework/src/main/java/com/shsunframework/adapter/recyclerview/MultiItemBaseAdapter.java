package com.shsunframework.adapter.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import com.shsunframework.adapter.BaseViewHolder;

import java.util.List;
//import com.hbung.adapter.BaseViewHolder;

public abstract class MultiItemBaseAdapter<T> extends BaseRecyclerViewAdapter<T> {


    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemBaseAdapter(Context context,
                                MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;

        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiItemTypeSupport == null) return super.onCreateViewHolder(parent, viewType);

        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        BaseViewHolder holder = BaseViewHolder.getViewHolder(mContext, null, parent, layoutId, -1);
        return holder;
    }
}
