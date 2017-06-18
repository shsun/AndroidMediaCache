package com.base.adapter.abslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.base.adapter.BaseViewHolder;

import java.util.List;

/**
 * @param <T>
 * @author shsun
 */
public abstract class MultiItemBaseAdapter<T> extends BaseAbListViewAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemBaseAdapter(Context context, List<T> datas,
                                MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getViewTypeCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position,
                    mDatas.get(position));
        return super.getItemViewType(position);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mMultiItemTypeSupport == null)
            return super.getView(position, convertView, parent);

        int layoutId = mMultiItemTypeSupport.getLayoutId(position,
                getItem(position));
        BaseViewHolder baseViewHolder = BaseViewHolder.getViewHolder(mContext, convertView, parent,
                layoutId, position);
        convert(baseViewHolder, getItem(position));
        return baseViewHolder.getConvertView();
    }

}
