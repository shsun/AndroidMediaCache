package com.base.app;

import com.base.BaseApplication;

import android.app.Activity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by shsun on 17/2/18.
 */

public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

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

        EventBus.getDefault().unregister(this);

        ((BaseApplication) this.getApplication()).finishActivity(this);
    }
}
