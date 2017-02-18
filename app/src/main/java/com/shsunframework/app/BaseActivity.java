package com.shsunframework.app;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class BaseActivity extends Activity {


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
