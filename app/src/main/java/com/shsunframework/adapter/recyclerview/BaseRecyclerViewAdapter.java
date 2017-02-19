package com.shsunframework.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.shsunframework.adapter.BaseViewHolder;
import com.shsunframework.adapter.recyclerview.click.OnItemClickListener;
import com.shsunframework.adapter.recyclerview.click.OnItemLongClickListener;

import java.util.List;

/**
 *
 */
public abstract class BaseRecyclerViewAdapter<T> extends HeaderAndFooterAdapter<BaseViewHolder> {

    protected Context mContext;

    protected int mLayoutId;

    protected List<T> mDatas;

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    /**
     * onCreateViewHolder
     *
     * @param context
     * @param layoutId
     * @param list
     */
    public BaseRecyclerViewAdapter(Context context, int layoutId, List<T> list) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = list;

        setHasStableIds(true);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = BaseViewHolder.getViewHolder(mContext, null, parent, mLayoutId, -1);
        this.setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    /**
     * update UI with data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }


    @Override
    public long getItemId(int position) {
        return mDatas.get(position).hashCode();
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    /**
     * do update UI with data
     * @param holder
     * @param t
     */
    public abstract void convert(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    protected void setListener(final ViewGroup parent, final BaseViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        if (mOnItemLongClickListener != null || mOnItemClickListener != null) {
            viewHolder.setItemBackgound();
        }
        if (mOnItemClickListener != null) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position - getmHeaderSize()), position - getmHeaderSize());
                }
            });
        }

        if (mOnItemLongClickListener != null) {
            viewHolder.getConvertView().setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int position = getPosition(viewHolder);
                            return mOnItemLongClickListener.onItemLongClick(parent, v, mDatas.get(position - getmHeaderSize()), position - getmHeaderSize());
                        }
                    }
            );
        }
    }


    public T getItemData(int position) {
        if (mDatas != null && mDatas.size() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }
}