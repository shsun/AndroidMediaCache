package com.base.controller;

import com.base.XBaseApplication;
import com.base.net.XRequestManager;

import android.app.Activity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class XBaseActivity extends Activity implements IXController {

    /**
     *
     */
    protected XRequestManager requestManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestManager = new XRequestManager();
        EventBus.getDefault().register(this);
        ((XBaseApplication) this.getApplication()).addActivity(this);

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
        this.cancelAllRequest();

        EventBus.getDefault().unregister(this);
        ((XBaseApplication) this.getApplication()).finishActivity(this);

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        this.cancelAllRequest();
        super.onPause();
    }

    @Override
    public XRequestManager getRequestManager() {
        return requestManager;
    }

    private void cancelAllRequest() {
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
    }
}
