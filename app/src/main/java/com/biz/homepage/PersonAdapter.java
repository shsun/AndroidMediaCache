package com.biz.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.biz.R;

import java.util.List;

/**
 * Created by shsun on 17/1/18.
 */
public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PersonAdapter.class.getSimpleName();

    public static interface OnRecyclerViewListener {
        void onItemClick(int position, PersonEntry entry);

        boolean onItemLongClick(int position, PersonEntry entry);
    }

    private final Context mContext;
    private List<PersonEntry> mDataEntries;

    private OnRecyclerViewListener onRecyclerViewListener;

    /**
     * @param context
     * @param list
     */
    public PersonAdapter(Context context, List<PersonEntry> list) {
        this.mContext = context;
        this.mDataEntries = list;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.common_listview_item_content, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

        public PersonViewHolder(View itemView) {
            super(itemView);

            // TODO
            mNameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            mDesTextView = (TextView) itemView.findViewById(R.id.desTextView);
            //
            mRootView = itemView.findViewById(R.id.contentView);
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onRecyclerViewListener) {
                        onRecyclerViewListener.onItemClick(position, mDataEntries.get(position));
                    }
                }
            });
            mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (null != onRecyclerViewListener) {
                        return onRecyclerViewListener.onItemLongClick(position, mDataEntries.get(position));
                    }
                    return false;
                }
            });
        }
    }
}