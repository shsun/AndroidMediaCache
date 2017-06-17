package com.biz.adapter;

import java.util.List;

import com.biz.R;
import com.biz.entry.PersonEntry;
import com.base.adapter.recyclerview.OnRecyclerViewItemListener;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by shsun on 17/1/18.
 */
public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PersonAdapter.class.getSimpleName();

    private final Context mContext;
    private List<PersonEntry> mDatas;

    private OnRecyclerViewItemListener<PersonEntry> mOnRecyclerViewItemListener;

    /**
     * @param context
     * @param datas
     */
    public PersonAdapter(Context context, List<PersonEntry> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    public void setOnRecyclerViewItemListener(OnRecyclerViewItemListener<PersonEntry> listener){
        this.mOnRecyclerViewItemListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.common_listview_item_content, null);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.position = i;
        PersonEntry person = mDatas.get(i);
        holder.mImageView.setImageURI(Uri.parse(person.getImageUrl()));
        holder.mNameTextView.setText(person.getName());
        // holder.mDesTextView.setText(person.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     *
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        public View mRootView;

        public SimpleDraweeView mImageView;
        public TextView mNameTextView;
        // public TextView mDesTextView;

        public int position;

        public ViewHolder(final View itemView) {
            super(itemView);

            final ViewGroup parent = (ViewGroup) itemView.getParent();

            //
            mRootView = itemView.findViewById(R.id.contentView);
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnRecyclerViewItemListener) {
                        mOnRecyclerViewItemListener.onItemClick(parent, itemView, mDatas.get(position), position);
                    }
                }
            });
            mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (null != mOnRecyclerViewItemListener) {
                        return mOnRecyclerViewItemListener
                                .onItemLongClick(parent, itemView, mDatas.get(position), position);
                    }
                    return false;
                }
            });

            mImageView = (SimpleDraweeView) itemView.findViewById(R.id.iconView);
            mNameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            //mDesTextView = (TextView) itemView.findViewById(R.id.desTextView);
        }
    }
}