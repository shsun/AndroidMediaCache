package com.shsunframework.app;

import com.shsunframework.BaseApplication;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by shsun on 17/2/18.
 */

public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication) this.getApplication()).addActivity(this);


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
        ((BaseApplication) this.getApplication()).finishActivity(this);
    }
}
