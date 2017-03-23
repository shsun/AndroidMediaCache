package com.shsunframework.adapter.recyclerview;

import java.util.ArrayList;
import java.util.List;

import com.shsunframework.adapter.BaseViewHolder;
import com.shsunframework.data.OnAdapterChangedListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class BaseRecyclerViewAdapter<T> extends HeaderAndFooterAdapter<BaseViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;

    protected OnRecyclerViewItemListener mOnRecyclerViewItemListener;

    protected OnAdapterChangedListener mOnAdapterChangedListener;

    /**
     * onCreateViewHolder
     *
     * @param context
     * @param layoutId
     */
    public BaseRecyclerViewAdapter(Context context, int layoutId) {
        mContext = context;
        mLayoutId = layoutId;
        this.mDatas = new ArrayList<T>();
        setHasStableIds(true);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = BaseViewHolder.getViewHolder(mContext, null, parent, mLayoutId, -1);
        baseViewHolder = doCreateViewHolder(baseViewHolder);
        this.setListener(parent, baseViewHolder, viewType);
        return baseViewHolder;
    }

    public abstract BaseViewHolder doCreateViewHolder(BaseViewHolder holder);

    /**
     * update UI with data
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.updatePosition(position);
        doBindViewHolder(holder, mDatas.get(position));
    }

    /**
     * do update UI with data
     *
     * @param holder
     * @param t
     */
    public abstract void doBindViewHolder(BaseViewHolder holder, T t);

    public void setOnRecyclerViewItemListener(OnRecyclerViewItemListener<T> listener) {
        this.mOnRecyclerViewItemListener = listener;
    }

    protected void setListener(final ViewGroup parent, final BaseViewHolder baseViewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }
        if (mOnRecyclerViewItemListener != null) {
            baseViewHolder.setItemBackgound();
        }
        if (mOnRecyclerViewItemListener != null) {
            baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getPosition(baseViewHolder);
                    mOnRecyclerViewItemListener.onItemClick(parent, v, mDatas.get(position - getmHeaderSize()),
                            position - getmHeaderSize());
                }
            });
        }

        if (mOnRecyclerViewItemListener != null) {
            baseViewHolder.getConvertView().setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int position = getPosition(baseViewHolder);
                            return mOnRecyclerViewItemListener
                                    .onItemLongClick(parent, v, mDatas.get(position - getmHeaderSize()),
                                            position - getmHeaderSize());
                        }
                    }
            );
        }
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

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return mDatas.get(position).hashCode();
    }


    public int getItemIndex(T data){
        return mDatas.indexOf(data);
    }

    public T getItem(int position) {
        if (mDatas != null && mDatas.size() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    public void setDataProvider(List<T> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
    }

    public List<T> getDataProvider() {
        return mDatas;
    }


    public void insert(int position, T item) {
        this.mDatas.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        this.mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void change(int position) {
        this.mDatas.remove(position);
        notifyItemChanged(position);
    }
}