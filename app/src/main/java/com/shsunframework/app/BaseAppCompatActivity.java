package com.shsunframework.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = this.getIntent().getExtras();
        initVariables(savedInstanceState, bundle);
        initView(savedInstanceState, bundle);
        initData(savedInstanceState, bundle);
    }

    protected abstract void initVariables(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initView(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initData(Bundle savedInstanceState, Bundle prevInstanceState);
}
