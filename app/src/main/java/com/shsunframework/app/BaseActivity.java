package com.shsunframework.app;

import android.app.Activity;
import android.os.Bundle;

import com.biz.CZSZApplication;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CZSZApplication) this.getApplication()).addActivity(this);


        Bundle bundle = this.getIntent().getExtras();
        //
        initVariables(savedInstanceState, bundle);
        initView(savedInstanceState, bundle);
        initData(savedInstanceState, bundle);
    }

    protected abstract void initVariables(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initView(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initData(Bundle savedInstanceState, Bundle prevInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((CZSZApplication) this.getApplication()).finishActivity(this);
    }
}
