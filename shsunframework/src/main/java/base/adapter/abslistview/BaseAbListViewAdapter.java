package base.adapter.abslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import base.adapter.BaseViewHolder;

import java.util.List;

/**
 * @author shsun
 *
 * @param <T>
 */
public abstract class BaseAbListViewAdapter<T> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    private int layoutId;

    public int getLayoutId() {
        return layoutId;
    }

    public BaseAbListViewAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    public int getmLayoutId() {
        return layoutId;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.getViewHolder(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(BaseViewHolder holder, T t);

}
