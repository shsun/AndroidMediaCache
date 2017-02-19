package com.shsunframework.adapter.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import com.shsunframework.adapter.BaseViewHolder;
//import com.hbung.adapter.BaseViewHolder;

import java.util.List;

public abstract class MultiItemBaseAdapter<T> extends BaseRecyclerViewAdapter<T> {


    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemBaseAdapter(Context context, List<T> datas,
                                MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, datas);
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
