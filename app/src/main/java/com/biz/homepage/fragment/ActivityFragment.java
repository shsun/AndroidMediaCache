package com.biz.homepage.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.biz.core.AbstractBasicFragment;


/**
 * Created by shsun on 17/1/12.
 */
public class ActivityFragment extends AbstractBasicFragment {

    private TextView mTextView;

    @Override
    public View initView() {
        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);

        mTextView.setTextSize(20);
        mTextView.setTextColor(Color.BLACK);

        return mTextView;
    }

    @Override
    public void initData() {
        Toast.makeText(getActivity(), "加载了活动数据", Toast.LENGTH_SHORT).show();
        mTextView.setText("活动视图");
    }

}
