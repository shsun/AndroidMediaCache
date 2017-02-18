package com.shsunframework.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables(savedInstanceState);
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract void initVariables(Bundle savedInstanceState);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);
}
