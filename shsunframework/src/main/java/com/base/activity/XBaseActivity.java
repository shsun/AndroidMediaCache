package com.base.activity;

import com.base.BaseApplication;
import com.base.net.RequestManager;

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
    protected RequestManager requestManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestManager = new RequestManager();
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
        this.cancelAllRequest();

        EventBus.getDefault().unregister(this);
        ((BaseApplication) this.getApplication()).finishActivity(this);

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        this.cancelAllRequest();
        super.onPause();
    }

    @Override
    public RequestManager getRequestManager() {
        return requestManager;
    }

    private void cancelAllRequest() {
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
    }
}
