package com.biz.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import base.controller.XBaseFragment;

/**
 * Created by shsun on 17/1/12.
 */
public class AmuseFragment extends XBaseFragment {

    public static final String TAG = AmuseFragment.class.getSimpleName();


    private TextView mTextView;

    @Override
    public View initView(Bundle bundle, LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);

        mTextView.setTextSize(20);
        mTextView.setTextColor(Color.BLACK);
        return mTextView;
    }

    @Override
    public void initData(Bundle bundle) {
        Toast.makeText(getActivity(), "加载了娱乐数据", Toast.LENGTH_SHORT).show();
        mTextView.setText("娱乐视图");
    }

    @Override
    protected void onInvisible() {

    }
}
