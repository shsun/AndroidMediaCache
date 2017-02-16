package com.biz.homepage.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import com.biz.R;
import com.biz.core.AbstractBasicFragment;

/**
 * Created by shsun on 17/1/12.
 */
public class NewestFragment extends AbstractBasicFragment {

    private static final String TAG = "NewestFragment";

    private TextView mTextView;

    private RecyclerView mRecyclerView;

    @Override
    public View initView() {

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.common_fragment_item, null);

        this.mRecyclerView  = (RecyclerView)layout.findViewById(R.id.recyclerView);

        this.mRecyclerView.setAdapter();


        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(20);
        mTextView.setTextColor(Color.BLACK);
        return mTextView;
    }

    @Override
    public void initData() {
        Toast.makeText(getActivity(), "加载了最新数据", Toast.LENGTH_SHORT).show();
        mTextView.setText("最新视图");


    }
}
