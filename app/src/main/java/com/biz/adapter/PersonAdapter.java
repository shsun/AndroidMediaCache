package com.biz.adapter;

import java.util.List;

import com.biz.R;
import com.biz.entry.PersonEntry;
import com.shsunframework.adapter.recyclerview.OnRecyclerViewItemListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by shsun on 17/1/18.
 */
public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PersonAdapter.class.getSimpleName();

    private final Context mContext;
    private List<PersonEntry> mDataEntries;

    private OnRecyclerViewItemListener<PersonEntry> mOnRecyclerViewItemListener;

    /**
     * @param context
     * @param list
     */
    public PersonAdapter(Context context, List<PersonEntry> list) {
        this.mContext = context;
        this.mDataEntries = list;
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
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = i;
        PersonEntry person = mDataEntries.get(i);
        holder.mNameTextView.setText(person.getName());
        holder.mDesTextView.setText(person.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataEntries.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        public View mRootView;
        public TextView mNameTextView;
        public TextView mDesTextView;
        public int position;

        public PersonViewHolder(final View itemView) {
            super(itemView);

            final ViewGroup parent = (ViewGroup) itemView.getParent();

            // TODO
            mNameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            mDesTextView = (TextView) itemView.findViewById(R.id.desTextView);
            //
            mRootView = itemView.findViewById(R.id.contentView);
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnRecyclerViewItemListener) {
                        mOnRecyclerViewItemListener.onItemClick(parent, itemView, mDataEntries.get(position), position);
                    }
                }
            });
            mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (null != mOnRecyclerViewItemListener) {
                        return mOnRecyclerViewItemListener
                                .onItemLongClick(parent, itemView, mDataEntries.get(position), position);
                    }
                    return false;
                }
            });
        }
    }
}